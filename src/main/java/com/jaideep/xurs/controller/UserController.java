package com.jaideep.xurs.controller;

import com.jaideep.xurs.entity.User;
import com.jaideep.xurs.exception.UserNotFoundException;
import com.jaideep.xurs.payload.RegisterPayload;
import com.jaideep.xurs.payload.UserPayload;
import com.jaideep.xurs.payload.UsernamePayload;
import com.jaideep.xurs.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/xtra/user")
public class UserController {

    private final UserService userService;

    @PutMapping("/setUsername")
    public ResponseEntity<?> setUsername(@RequestBody UsernamePayload usernamePayload) {
        try {
            User user = userService.setUsername(usernamePayload.getUserId(), usernamePayload.getUsername());
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("No user found");
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/getUserByEmail")
    public ResponseEntity<?> getUserByEmail(@RequestHeader("email") String email) {
        try {
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("No user found");
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/get")
    public ResponseEntity<?> getUser(@RequestHeader("userId") String userId) {
        try {
            User user = userService.getUser(userId);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("No user found");
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserPayload userPayload) {
        try {
            User updatedUser = userService.updateUser(userPayload);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("No user found");
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        try {
            User user = userService.deleteUser(userId);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("No user found");
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid RegisterPayload registerPayload) {
        try {
            User user = userService.createUser(registerPayload);
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

}
