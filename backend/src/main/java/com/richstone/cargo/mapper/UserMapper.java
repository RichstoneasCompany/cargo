package com.richstone.cargo.mapper;

import com.richstone.cargo.dto.UserDetailsDto;
import com.richstone.cargo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class );
    UserDetailsDto toDto(User user);
}
