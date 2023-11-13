package com.nhomA.mockproject.repository;

import com.nhomA.mockproject.entity.Address;
import com.nhomA.mockproject.entity.CartLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByIdAndUserId (Long id, Long userId);
}
