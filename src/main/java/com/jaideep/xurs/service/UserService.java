package com.jaideep.xurs.service;

import com.jaideep.xurs.entity.User;
import com.jaideep.xurs.exception.UserNotFoundException;
import com.jaideep.xurs.payload.RegisterPayload;
import com.jaideep.xurs.payload.UserPayload;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    User setUsername(String userId, String username) throws UserNotFoundException;

    User createUser(RegisterPayload registerPayload);

    User updateUser(UserPayload userPayload) throws UserNotFoundException;

    User getUser(String userId) throws UserNotFoundException;

    User deleteUser(String userId) throws UserNotFoundException;

    User getUserByEmail(String email) throws UserNotFoundException;

    List<User> getAllUsers();

    Boolean setProfilePic(MultipartFile file);
}

