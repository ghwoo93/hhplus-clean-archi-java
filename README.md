# Getting Started

### ERD

```Mermaid
erDiagram
    User {
        BIGINT user_id
        VARCHAR username
    }

    Lecture {
        BIGINT lecture_id
        VARCHAR title
        TIMESTAMP date
        INT capacity
    }

    LectureApplication {
        BIGINT application_id
        BIGINT user_id
        BIGINT lecture_id
        TIMESTAMP application_date
    }

    User ||--o{ LectureApplication: "신청한다"
    Lecture ||--o{ LectureApplication: "포함한다"
```
