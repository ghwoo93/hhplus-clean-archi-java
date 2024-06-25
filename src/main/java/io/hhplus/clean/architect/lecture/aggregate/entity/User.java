package io.hhplus.clean.architect.lecture.aggregate.entity;


import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public record User(
    @Id Long userId,
    String username
) implements Serializable {}