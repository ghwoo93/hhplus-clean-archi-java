# 특강 신청 서비스

## Description
- 특강 신청 서비스를 구현해 봅니다.
- 항해 플러스 토요일 특강을 신청할 수 있는 서비스를 개발합니다.
- 특강 신청 및 신청자 목록 관리를 RDBMS를 이용해 관리할 방법을 고민합니다.

## Requirements
- 아래 2가지 API 를 구현합니다.
    - 특강 신청 API
    - 특강 신청 여부 조회 API
- 각 기능 및 제약 사항에 대해 단위 테스트를 반드시 하나 이상 작성하도록 합니다.
- 다수의 인스턴스로 어플리케이션이 동작하더라도 기능에 문제가 없도록 작성하도록 합니다.
- 동시성 이슈를 고려하여 구현합니다.

## 심화 과제
- (필수) 특강 선택 API GET /lectures
    - 단 한번의 특강을 위한 것이 아닌 날짜별로 특강이 존재할 수 있는 범용적인 서비스로 변화시켜 봅니다.
    - 이를 수용하기 위해, 특강 엔티티의 경우 기존의 설계에서 변경되어야 합니다.
    - 특강의 정원은 30명으로 고정이며, 사용자는 각 특강에 신청하기전 목록을 조회해볼 수 있어야 합니다.
        - 추가로 정원이 특강마다 다르다면 어떻게 처리할것인가..? 고민해 보셔라~

## 요구사항 분석

### 만들어야하는 API
- 특강 신청 가능 목록 조회
- 특강 신청
- 특강 신청 완료 여부 조회

### 설계 시 주안점
- 레이어 구조 (프레젠테이션, 애플리케이션, 도메인, 인프라)로 책임 분리
- 의존성 역전 원칙(DIP) 적용
- FK 배제, 애플리케이션 레벨에서 정합성 관리
- 비관적 락으로 동시성 문제 해결
- 트랜잭션 관리로 데이터 일관성 유지

### TC 작성 시나리오
1. LectureServiceTest:
    - 특강 목록 조회 테스트: 모든 특강 목록이 올바르게 반환되는지 확인합니다.
    - 특정 강의의 일정 조회 테스트: 특정 강의의 일정 목록이 올바르게 반환되는지 확인합니다.
    - 특강 신청 테스트: 사용자가 특강에 성공적으로 신청할 수 있는지 확인합니다. 동시에 동시성 및 순차 처리 문제가 없는지 검증합니다.
    - 특강 신청 완료 여부 조회 테스트: 사용자의 특강 신청 완료 여부가 올바르게 반환되는지 확인합니다.
    - 특정 일정의 등록된 사용자 목록 조회 테스트: 특정 일정에 등록된 사용자 목록이 올바르게 반환되는지 확인합니다.

2. LectureControllerTest:
    - 특강 목록 조회 API 테스트: 올바른 특강 목록이 반환되는지 확인합니다.
    - 특정 강의의 일정 조회 API 테스트: 특정 강의의 일정 목록이 올바르게 반환되는지 확인합니다.
    - 특강 신청 API 테스트: 사용자가 특강에 성공적으로 신청할 수 있는지 확인합니다. 동시에 동시성 및 순차 처리 문제가 없는지 검증합니다.
    - 특강 신청 완료 여부 조회 API 테스트: 사용자의 특강 신청 완료 여부가 올바르게 반환되는지 확인합니다.
    - 특정 일정의 등록된 사용자 목록 조회 API 테스트: 특정 일정에 등록된 사용자 목록이 올바르게 반환되는지 확인합니다.

### ERD

```Mermaid
erDiagram
    LECTURE {
        BIGINT lecture_id PK "AUTO_INCREMENT"
        VARCHAR title "NOT NULL"
    }

    LECTURE_SCHEDULE {
        BIGINT schedule_id PK "AUTO_INCREMENT"
        BIGINT lecture_id FK "NOT NULL"
        TIMESTAMP date "NOT NULL"
        INT capacity "NOT NULL"
    }

    LECTURE_REGISTRATION {
        BIGINT registration_id PK "AUTO_INCREMENT"
        BIGINT user_id "NOT NULL" PK "AUTO_INCREMENT"
        BIGINT schedule_id FK "NOT NULL"
        TIMESTAMP registration_date "NOT NULL"
    }
```
![image](https://github.com/ghwoo93/hhplus-lecture-2week/assets/26402238/c24c7b34-d918-4f7a-adea-6c9afe23bc8b)

- 테이블 간 관계성 배제 목적 
    - 관계성을 배제하고 비즈니스 로직으로 데이터 무결성을 보완하였습니다.
    - 복잡한 조인을 감소시켜 고부하를 견딜 수 있도록 설계하였습니다.
    - 향후 분산 처리같은 확장성을 고려하였습니다.
- 테이블 설명
    - LECTURE: 특강에 대한 기본 정보를 저장합니다. 
    - LECTURE_SCHEDULE: 각 특강의 일정과 관련된 정보를 저장합니다. 
    - LECTURE_REGISTRATION: 각 특강 일정에 대한 수강 신청 정보를 저장합니다. 

### Architecture
![image](https://github.com/ghwoo93/hhplus-lecture-2week/assets/26402238/381558af-3589-4a69-a49a-df0301c7690b)


### 디렉터리 구조

```Plain Text
├─main
│  ├─java
│  │  └─io
│  │      └─hhplus
│  │          └─clean
│  │              └─architect
│  │                  │  Application.java - 애플리케이션의 메인 엔트리 포인트
│  │                  │
│  │                  └─lecture
│  │                      ├─adapter - 외부 시스템 및 API와 인터페이스
│  │                      │  ├─controller - HTTP 요청을 처리하고 서비스를 매핑
│  │                      │  │      LectureController.java
│  │                      │  │
│  │                      │  ├─mapper - 엔티티와 DTO 간의 매핑 처리
│  │                      │  │      LectureMapper.java
│  │                      │  │      LectureRegistrationMapper.java
│  │                      │  │      LectureScheduleMapper.java
│  │                      │  │
│  │                      │  └─repository - 리포지토리 인터페이스
│  │                      │          LectureRegistrationRepository.java
│  │                      │          LectureRepository.java
│  │                      │          LectureScheduleRepository.java
│  │                      │
│  │                      ├─application - 애플리케이션 레벨의 서비스 포함
│  │                      │      LectureService.java - 도메인 서비스 및 리포지토리 조정
│  │                      │
│  │                      ├─domain - 핵심 비즈니스 로직 및 도메인 모델
│  │                      │  ├─dto - 레이어 간 데이터 전송 객체
│  │                      │  │      LectureDTO.java
│  │                      │  │      LectureRegistrationDTO.java
│  │                      │  │      LectureScheduleDTO.java
│  │                      │  │
│  │                      │  ├─model - 핵심 비즈니스 엔터티를 나타내는 도메인 모델
│  │                      │  │      Lecture.java
│  │                      │  │      LectureRegistration.java
│  │                      │  │      LectureRegistrationId.java
│  │                      │  │      LectureSchedule.java
│  │                      │  │
│  │                      │  ├─service - 비즈니스 로직을 처리하는 도메인 서비스
│  │                      │  │      LectureDomainService.java
│  │                      │  │
│  │                      │  └─vo - 불변 데이터 구조를 나타내는 값 객체
│  │                      │          LectureInfo.java
│  │                      │          LectureRegistrationInfo.java
│  │                      │          LectureScheduleInfo.java
│  │                      │
│  │                      ├─exception - 특정 오류 상황을 처리하는 커스텀 예외
│  │                      │      LectureBusinessException.java
│  │                      │      LectureInterfaceException.java
│  │                      │
│  │                      ├─helper - 유틸리티 클래스 및 헬퍼
│  │                      │      FairLockHelper.java
│  │                      │
│  │                      └─infra - 리포지토리 구현을 포함하는 인프라 레이어
│  │                          └─repository
│  │                                  LectureRegistrationRepositoryImpl.java
│  │                                  LectureRepositoryImpl.java
│  │                                  LectureScheduleRepositoryImpl.java
│  │
│  └─resources - 설정 파일 및 스크립트
│          application.properties
│          data.sql
│          schema.sql
│
└─test - 애플리케이션의 다양한 레이어에 대한 테스트 케이스
    └─java
        └─io
            └─hhplus
                └─clean
                    └─architect
                        │  ApplicationTests.java
                        │
                        └─lecture
                                LectureControllerTest.java
                                LectureServiceTest.java
```

# API

1. 특강 목록 조회 API (`GET /api/lectures`)
    
    ```bash
    curl -X GET "http://localhost:8080/api/lectures" -H "Content-Type: application/json"
    ```
    
2. 특정 강의의 일정 조회 API (`GET /api/lectures/{lectureId}/schedules`)
    
    ```bash
    curl -X GET "http://localhost:8080/api/lectures/1/schedules" -H "Content-Type: application/json"
    ```
    
3. **특강 신청 API (`POST /api/lectures/apply`)**
    
    ```bash
    curl -X POST "http://localhost:8080/api/lectures/apply" -H "Content-Type: application/x-www-form-urlencoded" -d "userId=1&scheduleId=1"
    ```
    
4. **특강 신청 완료 여부 조회 API (`GET /api/lectures/application/{userId}`)**
    
    ```bash
    curl -X GET "http://localhost:8080/api/lectures/application/1" -H "Content-Type: application/json"
    ```
    
5. **특정 일정의 등록된 사용자 목록 조회 API (`GET /api/lectures/{scheduleId}/registrations`)**
    
    ```wasm
    curl -X GET "http://localhost:8080/api/lectures/1/registrations" -H "Content-Type: application/json"
    ```
