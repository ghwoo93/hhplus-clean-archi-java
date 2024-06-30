package io.hhplus.clean.architect.lecture.infra.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import io.hhplus.clean.architect.lecture.adapter.repository.LectureRegistrationRepository;
import io.hhplus.clean.architect.lecture.domain.model.LectureRegistration;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class LectureRegistrationRepositoryImpl implements LectureRegistrationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean existsByUserIdAndScheduleId(Long userId, Long scheduleId) {
        String query = "SELECT COUNT(lr) > 0 FROM LectureRegistration lr WHERE lr.userId = :userId AND lr.scheduleId = :scheduleId";
        Boolean result = entityManager.createQuery(query, Boolean.class)
                .setParameter("userId", userId)
                .setParameter("scheduleId", scheduleId)
                .getSingleResult();
        return result;
    }

    @Override
    public boolean existsByUserId(Long userId) {
        String query = "SELECT COUNT(lr) > 0 FROM LectureRegistration lr WHERE lr.userId = :userId";
        Boolean result = entityManager.createQuery(query, Boolean.class)
                .setParameter("userId", userId)
                .getSingleResult();
        return result;
    }

    @Override
    public long countByScheduleId(Long scheduleId) {
        String query = "SELECT COUNT(lr) FROM LectureRegistration lr WHERE lr.scheduleId = :scheduleId";
        Long result = entityManager.createQuery(query, Long.class)
                .setParameter("scheduleId", scheduleId)
                .getSingleResult();
        return result;
    }

    @Override
    public List<LectureRegistration> findByScheduleId(Long scheduleId) {
        String query = "SELECT lr FROM LectureRegistration lr WHERE lr.scheduleId = :scheduleId";
        return entityManager.createQuery(query, LectureRegistration.class)
                .setParameter("scheduleId", scheduleId)
                .getResultList();
    }

    @Override
    @Transactional
    public void save(LectureRegistration lectureRegistration) {
        entityManager.persist(lectureRegistration);
    }
}