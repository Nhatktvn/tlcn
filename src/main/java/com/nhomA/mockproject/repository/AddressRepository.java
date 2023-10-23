package com.nhomA.mockproject.repository;

import com.nhomA.mockproject.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
