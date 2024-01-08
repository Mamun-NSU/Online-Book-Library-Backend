package com.mamun.onlinebooklibrary.service.impl;

import com.mamun.onlinebooklibrary.entity.User;
import com.mamun.onlinebooklibrary.exception.EmailException;
import com.mamun.onlinebooklibrary.exception.PasswordException;
import com.mamun.onlinebooklibrary.model.ResponseDto;
import com.mamun.onlinebooklibrary.model.UserDto;
import com.mamun.onlinebooklibrary.repository.UserRepository;
import com.mamun.onlinebooklibrary.service.services.UserAuthService;
import com.mamun.onlinebooklibrary.utils.JWTUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAuthServiceImpl implements UserAuthService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public ResponseDto createUser(UserDto userDto) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        if (userRepository.findByEmail(userDto.getEmail()).isPresent())
            throw new EmailException();
        if (userDto.getPassword().length() < 5) {
            throw new PasswordException();
        }
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setAddress(userDto.getAddress());
        user.setRole(userDto.getRole());
        String publicUserId = JWTUtils.generateUserID(10);
        User storedUserDetails = userRepository.save(user);
        String accessToken = JWTUtils.generateToken(publicUserId);
        ResponseDto responseDto = modelMapper.map(storedUserDetails, ResponseDto.class);
        responseDto.setAccessToken(accessToken);
        return responseDto;
    }

    @Override
    public UserDto getUser(String email) {
        User user = userRepository.findByEmail(email).get();
        if (user == null) throw new UsernameNotFoundException("No record found");
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(user, returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(Integer userId) throws Exception {
        UserDto returnValue = new UserDto();
        User user = userRepository.findByUserId(userId).orElseThrow(Exception::new);
        BeanUtils.copyProperties(user, returnValue);
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
        authorities.add(grantedAuthority);
        if (user == null) throw new UsernameNotFoundException(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                true, true, true, true, authorities);
    }
}

