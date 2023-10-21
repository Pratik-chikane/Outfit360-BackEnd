package com.outfit360.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit360.model.Address;

public interface IAddressRepository extends JpaRepository<Address, Long> {
//	public Optional<Address> findById(Long id);

}
