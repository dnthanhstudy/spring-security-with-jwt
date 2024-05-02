package com.thanhstudy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class CustomerEntity extends BaseEntity {

	@Column(name = "username", nullable = false, unique = true)
	private String userName;

	@Column(name = "fullname", nullable = false)
	private String fullName;

	@Column(name = "password", nullable = false)
	private String password;
}
