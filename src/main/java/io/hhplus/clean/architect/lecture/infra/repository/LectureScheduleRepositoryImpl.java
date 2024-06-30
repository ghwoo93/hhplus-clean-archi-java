package io.hhplus.clean.architect.lecture.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import io.hhplus.clean.architect.lecture.adapter.repository.LectureScheduleRepository;
import io.hhplus.clean.architect.lecture.domain.model.LectureSchedule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class LectureScheduleRepositoryImpl implements LectureScheduleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<LectureSchedule> findByLectureId(Long lectureId) {
        return entityManager.createQuery("SELECT ls FROM LectureSchedule ls WHERE ls.lectureId = :lectureId", LectureSchedule.class)
                .setParameter("lectureId", lectureId)
                .getResultList();
    }

    @Override
    public Optional<LectureSchedule> findByIdWithLock(Long scheduleId, LockModeType lockModeType) {
        return Optional.ofNullable(entityManager.find(LectureSchedule.class, scheduleId, lockModeType));
    }

    @Override
    @Transactional
    public void save(LectureSchedule lectureSchedule) {
        entityManager.persist(lectureSchedule);
    }

    @Override
    public List<LectureSchedule> findAll() {
        return entityManager.createQuery("SELECT ls FROM LectureSchedule ls", LectureSchedule.class)
                            .getResultList();
    }
}
