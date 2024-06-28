package io.hhplus.clean.architect.lecture.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import io.hhplus.clean.architect.lecture.aggregate.entity.LectureSchedule;
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
    public Optional<LectureSchedule> findById(Long scheduleId) {
        LectureSchedule schedule = entityManager.find(LectureSchedule.class, scheduleId, LockModeType.PESSIMISTIC_WRITE);
        return Optional.ofNullable(schedule);
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
