package com.example.gaming.service;

import com.example.gaming.model.BundleDto;
import com.example.gaming.model.GameDto;
import com.example.gaming.model.UserDto;
import com.example.gaming.payload.ApiResponse;
import com.example.gaming.security.UserPrincipal;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTests {

    @Autowired
    public UserService userService;

    private String username1, username2, availableUsername, email, availableEmail;

    private UserPrincipal userPrincipal;

    private UserDto userDto;

    @BeforeAll
    public void init() {
        username1 = "test1";
        username2 = "test2";
        availableUsername = "invalidTest1";

        email = "test1@gmail.com";
        availableEmail = "invalidTest1@gmail.com";

        userDto = userService.findByUsername(username1);
        userPrincipal = new UserPrincipal(userDto.getId(), userDto.getFirstName(), userDto.getLastName(), userDto.getUserName(), userDto.getEmail());
    }

    @Test
    @Order(1)
    public void testCheckUsernameAvailabilityAvailable() {
        ResponseEntity<?> responseEntity = userService.checkUsernameAvailability(availableUsername);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(apiResponse.getSuccess());
        assertEquals("Username is available", apiResponse.getMessage());
    }

    @Test
    @Order(2)
    public void testCheckUsernameAvailabilityUnavailable() {
        ResponseEntity<?> responseEntity = userService.checkUsernameAvailability(username1);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(apiResponse.getSuccess());
        assertEquals("Username is NOT available", apiResponse.getMessage());
    }

    @Test
    @Order(3)
    public void testCheckEmailAvailabilityAvailable() {
        ResponseEntity<?> responseEntity = userService.checkEmailAvailability(availableEmail);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(apiResponse.getSuccess());
        assertEquals("Email is available", apiResponse.getMessage());
    }

    @Test
    @Order(4)
    public void testCheckEmailAvailabilityUnavailable() {
        ResponseEntity<?> responseEntity = userService.checkEmailAvailability(email);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(apiResponse.getSuccess());
        assertEquals("Email is NOT available", apiResponse.getMessage());
    }

    @Test
    @Order(5)
    public void testGetGamesCreatedBy() {
        Iterable<GameDto> gameDtos = userService.getGamesCreatedBy(username2, userPrincipal);

        assertTrue(gameDtos.iterator().hasNext());
    }

    @Test
    @Order(6)
    @Transactional
    public void testGetBundlesCreatedBy() {
        Iterable<BundleDto> bundleDtos = userService.getBundlesCreatedBy(username2, userPrincipal);

        assertTrue(bundleDtos.iterator().hasNext());
    }

    @Test
    @Order(7)
    @Transactional
    @Rollback(false)
    public void testAddBundle() {
        List<Long> bundlesToAdd = new ArrayList<Long>();
        bundlesToAdd.add(4L);
        bundlesToAdd.add(5L);

        ResponseEntity<?> responseEntity = userService.addBundle(userPrincipal.getId(), bundlesToAdd);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(apiResponse.getSuccess());
        assertEquals("Bundle(s) successfully added to user", apiResponse.getMessage());
    }

    @Test
    @Order(8)
    public void testEditUser() {
        userDto = userService.findByUsername(username1);
        userDto.setBundles(new HashSet<BundleDto>());

        ResponseEntity<?> responseEntity = userService.editUser(userDto);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(apiResponse.getSuccess());
        assertEquals("User with id " + userDto.getId() + " was successfully edited", apiResponse.getMessage());
    }
}
