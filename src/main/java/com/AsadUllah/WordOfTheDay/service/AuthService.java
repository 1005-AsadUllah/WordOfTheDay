package com.AsadUllah.WordOfTheDay.service;

import com.AsadUllah.WordOfTheDay.Model.User;
import com.AsadUllah.WordOfTheDay.entity.UserEntity;
import com.AsadUllah.WordOfTheDay.mapper.UserMapper;
import com.AsadUllah.WordOfTheDay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        UserEntity userEntity = userMapper.userToUserEntity(user);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.userEntityToUser(userRepository.save(userEntity));
    }

}
