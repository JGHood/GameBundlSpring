package com.example.gaming.controller;

import com.example.gaming.model.BundleDto;
import com.example.gaming.model.GameDto;
import com.example.gaming.model.User;
import com.example.gaming.model.UserDto;
import com.example.gaming.payload.ApiResponse;
import com.example.gaming.repository.UserRepository;
import com.example.gaming.security.CurrentUser;
import com.example.gaming.security.UserPrincipal;
import com.example.gaming.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // get current user information
    @GetMapping(path = "/user/me")
    public UserDto getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return userService.getCurrentUser(currentUser);
    }


    // check if a username is available to use
    @GetMapping(path = "/user/checkUsernameAvailability")
    public ResponseEntity<?> checkUsernameAvailability(@RequestParam(value = "username") String username) {
        return userService.checkUsernameAvailability(username);
    }

    // check is an email is available to use
    @GetMapping("/user/checkEmailAvailability")
    public ResponseEntity<?> checkEmailAvailability(@RequestParam(value = "email") String email) {
        return userService.checkEmailAvailability(email);
    }

    // get games the specified employee created
    @GetMapping("/user/games")
    public Iterable<GameDto> getGamesCreatedBy(@RequestParam(value = "username") String username,
                                               @CurrentUser UserPrincipal currentUser) {
        return userService.getGamesCreatedBy(username, currentUser);
    }

    // get bundles a specific user (customer or employee) created
    @GetMapping("/user/bundles")
    public Iterable<BundleDto> getBundlesCreatedBy(@RequestParam(value = "username") String username,
                                                   @CurrentUser UserPrincipal currentUser) {
        return userService.getBundlesCreatedBy(username, currentUser);
    }

    @PostMapping("/user/addBundle")
    public ResponseEntity<?> addBundle(@RequestParam(value = "userId") Long userId, @RequestBody List<Long> bundleIds) {
        return userService.addBundle(userId, bundleIds);
    }

    @PostMapping("/user/editUser")
    public ResponseEntity<?> editUser(@Valid @RequestBody UserDto userDto) {
        return userService.editUser(userDto);
    }
}
