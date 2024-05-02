package com.thanhstudy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thanhstudy.constant.StatusConstant;
import com.thanhstudy.entity.CustomerEntity;
import com.thanhstudy.model.dto.MyUser;
import com.thanhstudy.respository.CustomerRepository;

@Service(value = "customCustomerDetailsService")
public class CustomCustomerDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CustomCustomerDetailsService.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Load user by username: {}", username);
		CustomerEntity customerEntity = customerRepository.findByUserNameAndStatus(username, StatusConstant.STATUS_ACTIVE);
		if (customerEntity == null) {
			throw new UsernameNotFoundException("User name not found");
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
		MyUser myUserDetail = new MyUser(customerEntity.getUserName(), customerEntity.getPassword(), true, true, true, true,
				authorities);
		BeanUtils.copyProperties(customerEntity, myUserDetail);
		return myUserDetail;
	}

}
