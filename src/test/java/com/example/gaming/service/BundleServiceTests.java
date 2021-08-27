package com.example.gaming.service;

import com.example.gaming.model.BundleDto;
import com.example.gaming.model.GameDto;
import com.example.gaming.payload.ApiResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BundleServiceTests {

    @Autowired
    BundleService bundleService;

    public Long bundleId;
    public List<Long> bundleGameIds;

    @BeforeAll
    public void init() {
        bundleId = 7L;
        bundleGameIds = new ArrayList<>();
        bundleGameIds.add(6L);
        bundleGameIds.add(7L);
    }

    @Test
    @Transactional
    @Order(1)
    public void testGetBundleById() {
        BundleDto bundleDto = bundleService.getBundleById(bundleId);

        assertNotNull(bundleDto);
    }

    @Test
    @Transactional
    @Order(2)
    public void testGetAllBundles() {
        Iterable<BundleDto> bundleDtos = bundleService.getAllBundles();

        assertTrue(bundleDtos.iterator().hasNext());
    }

    @Test
    @Transactional
    @Order(3)
    public void testGetFeaturedBundles() {
        Iterable<BundleDto> bundleDtos = bundleService.getFeaturedBundles();

        assertTrue(bundleDtos.iterator().hasNext());
    }

    @Test
    @Transactional
    @Order(4)
    public void testGetEmployeeCreatedBundles() {
        Iterable<BundleDto> bundleDtos = bundleService.getEmployeeCreatedBundles();

        assertTrue(bundleDtos.iterator().hasNext());
    }

//    @Test
//    @Transactional
//    @Rollback(false)
//    @Order(5)
//    public void testAddGame() {
//        ResponseEntity<?> responseEntity = bundleService.addGame(bundleId, bundleGameIds);
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertTrue(apiResponse.getSuccess());
//        assertEquals("Game(s) successfully added to bundle", apiResponse.getMessage());
//    }
//
//    @Test
//    @Transactional
//    @Rollback(false)
//    @Order(6)
//    public void testGetGamesByBundle() {
//        Iterable<GameDto> gameDtos = bundleService.getGamesByBundle(bundleId);
//        List<Long> gameIds = new ArrayList<>();
//
//        gameDtos.forEach(g -> gameIds.add(g.getId()));
//
//        assertTrue(gameIds.containsAll(bundleGameIds));
//    }
//
//    @Test
//    @Transactional
//    @Rollback(false)
//    @Order(7)
//    public void testEditBundle() {
//        BundleDto bundleDto = bundleService.getBundleById(bundleId);
//        bundleDto.setGames(new HashSet<>());
//
//        ResponseEntity<?> responseEntity = bundleService.editBundle(bundleDto);
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertTrue(apiResponse.getSuccess());
//        assertEquals("Bundle with id " + bundleDto.getId() + " was successfully edited", apiResponse.getMessage());
//    }
}
