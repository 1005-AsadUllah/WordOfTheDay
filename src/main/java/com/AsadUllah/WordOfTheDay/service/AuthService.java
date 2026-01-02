package com.AsadUllah.WordOfTheDay.service;

import com.AsadUllah.WordOfTheDay.Model.User;
import com.AsadUllah.WordOfTheDay.entity.UserEntity;
import com.AsadUllah.WordOfTheDay.mapper.UserMapper;
import com.AsadUllah.WordOfTheDay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User registerUser(User user) {
        return userMapper.userEntityToUser(
                userRepository.save(userMapper.userToUserEntity(user))
        );
    }
}
