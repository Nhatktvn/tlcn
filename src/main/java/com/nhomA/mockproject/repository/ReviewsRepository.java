package com.nhomA.mockproject.repository;

import com.nhomA.mockproject.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
}
