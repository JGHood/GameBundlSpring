package com.example.gaming.controller;

import com.example.gaming.model.Bundle;
import com.example.gaming.model.BundleDto;
import com.example.gaming.model.Game;
import com.example.gaming.model.GameDto;
import com.example.gaming.payload.ApiResponse;
import com.example.gaming.payload.BundleRequest;
import com.example.gaming.repository.BundleRepository;
import com.example.gaming.repository.GameRepository;
import com.example.gaming.service.BundleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/bundle")
public class BundleController {

    @Autowired
    BundleService bundleService;

    // create a bundle
    @PostMapping("/create")
    public ResponseEntity<?> createBundle(@Valid @RequestBody BundleRequest bundleRequest) {
        return bundleService.createBundle(bundleRequest);
    }

    // add game to a bundle
    @PostMapping("/addGame")
    public ResponseEntity<?> addGame(@RequestParam(value = "bundleId") Long bundleId, @RequestBody List<Long> gameIds) {
        return bundleService.addGame(bundleId, gameIds);
    }

    // get bundle by id
    @GetMapping("/getBundleById")
    public BundleDto getBundleById(@RequestParam(value = "bundleId") Long bundleId) {
        return bundleService.getBundleById(bundleId);
    }

    // get all games that are a apart of a bundle
    @GetMapping("/getBundleGames")
    public @ResponseBody Iterable<GameDto> getGamesByBundle(@RequestParam(value = "bundleId") Long bundleId) {
        return bundleService.getGamesByBundle(bundleId);
    }

    // get all bundles
    @GetMapping("/getAllBundles")
    public @ResponseBody Iterable<BundleDto> getAllBundles() {
        return bundleService.getAllBundles();
    }

    // get all featured bundles
    @GetMapping("/getFeaturedBundles")
    public @ResponseBody Iterable<BundleDto> getFeaturedBundles() {
        return bundleService.getFeaturedBundles();
    }

    // get all bundles created by an employee
    @GetMapping("/getEmployeeCreatedBundles")
    public @ResponseBody Iterable<BundleDto> getAllEmployeeBundles() {
        return bundleService.getEmployeeCreatedBundles();
    }

    @PostMapping("/editBundle")
    public ResponseEntity<?> editBundle(@Valid @RequestBody BundleDto bundleDto) {
        return bundleService.editBundle(bundleDto);
    }
}
