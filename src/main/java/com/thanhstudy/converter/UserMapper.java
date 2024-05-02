package com.thanhstudy.converter;

import org.mapstruct.Mapper;

import com.thanhstudy.entity.UserEntity;
import com.thanhstudy.model.dto.MyUser;

@Mapper
public interface UserMapper {

	MyUser convertToDTO (UserEntity entity);
}
