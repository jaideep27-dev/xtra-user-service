package com.jaideep.xurs.service.impl;

import com.jaideep.xurs.entity.User;
import com.jaideep.xurs.exception.UserNotFoundException;
import com.jaideep.xurs.payload.RegisterPayload;
import com.jaideep.xurs.payload.UserPayload;
import com.jaideep.xurs.repository.UserRepository;
import com.jaideep.xurs.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User setUsername(String userId, String username) throws UserNotFoundException {
        User user = this.getUser(userId);
        user.setUsername(username);
        UserPayload userPayload = new UserPayload();
        BeanUtils.copyProperties(user, userPayload);
        return this.updateUser(userPayload);
    }

    @Override
    @Transactional
    public User createUser(RegisterPayload registerPayload) {
        User user = new User();
        // Setting creation and update date
        // Define the formatter for the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        // Get the current time as a LocalDateTime
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        // Format the LocalDateTime to the desired format
        String formattedDate = now.format(formatter);

        // Parse the formatted date-time string back into an Instant
        Instant currentTime = LocalDateTime.parse(formattedDate, formatter).toInstant(ZoneOffset.UTC);

        user.setCreatedAt(currentTime);
        user.setLastUpdated(currentTime);
        BeanUtils.copyProperties(registerPayload, user);
        log.info("Saving user to database");
        User savedUser = userRepository.save(user);
        log.info("Created user with userId : {}", user.getId());
        return savedUser;
    }

    @Override
    @Transactional
    public User updateUser(UserPayload userPayload) throws UserNotFoundException {
        log.info("Updating user details and saving to database");
        User user = this.getUser(userPayload.getId());
        BeanUtils.copyProperties(userPayload, user);
        user.setLastUpdated(Instant.now());
        User savedUser = userRepository.save(user);
        log.info("Updated user with id {}, successfully", savedUser.getId());
        return savedUser;
    }

    @Override
    public User getUser(String userId) throws UserNotFoundException {
        log.info("Retrieving user details for id : {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(String.format("No " + "user" + " found with id : %s", userId)));
        log.info("User found!");
        return user;
    }

    @Override
    @Transactional
    public User deleteUser(String userId) throws UserNotFoundException {
        log.info("Retrieving user for deletion for id : {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(String.format("No " + "user" + " found with id : %s", userId)));
        log.info("User found!, now deleting");
        userRepository.deleteById(userId);
        log.info("User deleted successfully");
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        log.info("Retrieving user using email");
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(String.format("No " + "user" + " found with email : %s", email)));
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Retrieving of all users");
        return userRepository.findAll();
    }

    @Override
    public Boolean setProfilePic(MultipartFile file) {
        return null;
    }
}
