package com.nhomA.mockproject.repository;

import com.nhomA.mockproject.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
}
