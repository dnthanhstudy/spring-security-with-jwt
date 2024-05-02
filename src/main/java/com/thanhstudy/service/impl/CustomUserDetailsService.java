package com.thanhstudy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

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
import com.thanhstudy.entity.UserEntity;
import com.thanhstudy.model.dto.MyUser;
import com.thanhstudy.respository.UserRepository;

@Service(value = "customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Load user by username: {}", username);
		UserEntity userEntity = userRepository.findByUserNameAndStatus(username, StatusConstant.STATUS_ACTIVE);
		if (userEntity == null) {
			throw new UsernameNotFoundException("User name not found");
		}
		List<GrantedAuthority> authorities = userEntity.getRoles().stream()
				.map(item -> new SimpleGrantedAuthority("ROLE_" + item.getCode())).collect(Collectors.toList());
		MyUser myUserDetail = new MyUser(userEntity.getUserName(), userEntity.getPassword(), true, true, true, true,
				authorities);
		BeanUtils.copyProperties(userEntity, myUserDetail);
		return myUserDetail;
	}

}
