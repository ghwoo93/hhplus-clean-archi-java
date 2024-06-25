package io.hhplus.clean.architect.lecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.hhplus.clean.architect.lecture.aggregate.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
