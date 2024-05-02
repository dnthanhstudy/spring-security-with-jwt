package com.thanhstudy.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thanhstudy.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByUserNameAndStatus (String userName, String status);
}
