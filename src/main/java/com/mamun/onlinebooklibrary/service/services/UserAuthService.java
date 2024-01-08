package com.mamun.onlinebooklibrary.service.services;

import com.mamun.onlinebooklibrary.entity.User;
import com.mamun.onlinebooklibrary.model.ResponseDto;
import com.mamun.onlinebooklibrary.model.UserDto;

import java.util.List;

public interface UserAuthService {
    ResponseDto createUser(UserDto user) throws Exception;

    UserDto getUser(String email);

    UserDto getUserByUserId(Integer id) throws Exception;

    List<User> findAllUsers();
}
