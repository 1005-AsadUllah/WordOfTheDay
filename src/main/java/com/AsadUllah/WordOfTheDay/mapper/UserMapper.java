package com.AsadUllah.WordOfTheDay.mapper;

import com.AsadUllah.WordOfTheDay.Model.User;
import com.AsadUllah.WordOfTheDay.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity userToUserEntity(User user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        return userEntity;
    }

    public User userEntityToUser(UserEntity userEntity) {
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        return user;
    }
}
