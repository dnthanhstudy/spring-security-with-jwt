package com.thanhstudy.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thanhstudy.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

	CustomerEntity findByUserNameAndStatus (String userName, String status);
}
