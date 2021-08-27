package com.example.gaming.service;

import com.example.gaming.model.*;
import com.example.gaming.payload.ApiResponse;
import com.example.gaming.repository.BundleRepository;
import com.example.gaming.repository.GameRepository;
import com.example.gaming.repository.UserRepository;
import com.example.gaming.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private BundleRepository bundleRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserDto findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        logger.debug("Got user information from repo");
        UserDto userDto = new UserDto(user.get().getId(), user.get().getFirstName(), user.get().getLastName(),
                user.get().getUsername(), user.get().getEmail());
        logger.debug("Created userdto base on user info");
        return userDto;
    }

    public Role findRoleById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.get().getRole();
    }

    public UserDto getCurrentUser(UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId()).get();
        UserDto userDto = new UserDto(currentUser.getId(), currentUser.getFirstName(), currentUser.getLastName(),
                currentUser.getUsername(), currentUser.getEmail(), user.getRole().getId());
        return userDto;
    }

    public ResponseEntity<?> checkUsernameAvailability(String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new ResponseEntity(new ApiResponse(isAvailable, isAvailable ? "Username is available" : "Username is NOT available"),
                HttpStatus.OK);
    }

    public ResponseEntity<?> checkEmailAvailability(String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new ResponseEntity(new ApiResponse(isAvailable, isAvailable ? "Email is available" : "Email is NOT available"),
                HttpStatus.OK);
    }

    public Iterable<GameDto> getGamesCreatedBy(String username, UserPrincipal userPrincipal) {
        Optional<User> user = userRepository.findByUsername(username);

        ArrayList<GameDto> gameDtos = new ArrayList<>();
        for (Game game : gameRepository.findByCreatedBy(user.get().getId())) {
            GameDto gameDto = new GameDto(game.getId(), game.getName(), game.getDeveloper(),
                    game.getPrice(), game.getReleaseDate(), game.getGenre());

            gameDtos.add(gameDto);
        }
        return gameDtos;
    }

    public Iterable<BundleDto> getBundlesCreatedBy(String username, UserPrincipal userPrincipal) {
        Optional<User> user = userRepository.findByUsername(username);

        ArrayList<BundleDto> bundleDtos = new ArrayList<>();
        for (Bundle bundle : bundleRepository.findByCreatedBy(user.get().getId())) {
            BundleDto bundleDto;
            Set<GameDto> gameDtos = new HashSet<>();

            for (Game game : bundle.getGames()) {
                GameDto gameDto = new GameDto(game.getId(), game.getName(), game.getDeveloper(), game.getPrice(),
                        game.getReleaseDate(), game.getGenre());

                gameDtos.add(gameDto);
            }

            bundleDto = new BundleDto(bundle.getId(), bundle.getName(), bundle.getPrice(),
                    bundle.getFeatured(), gameDtos, bundle.getDiscountRate());

            bundleDtos.add(bundleDto);
        }
        return bundleDtos;
    }

    public ResponseEntity<?> addBundle(Long userId, List<Long> bundleIds) {
        User user = userRepository.findById(userId).get();
        List<Bundle> bundles = new ArrayList<>();

        for (Long bundleId : bundleIds) {
            Bundle bundle = bundleRepository.findById(bundleId).get();
            bundles.add(bundle);
        }

        user.getBundles().addAll(bundles);
        userRepository.save(user);

        return new ResponseEntity(new ApiResponse(true, "Bundle(s) successfully added to user"),
                HttpStatus.OK);
    }

    public ResponseEntity<?> editUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).get();

        Set<Bundle> bundles = new HashSet<>();
        if (userDto.getBundles() != null) {
            for (BundleDto bundleDto : userDto.getBundles()) {
                Bundle bundle = bundleRepository.findById(bundleDto.getId()).get();
                bundles.add(bundle);
            }
        }

        user.setBundles(userDto.getBundles() == null ? user.getBundles() : bundles);

        userRepository.save(user);

        return new ResponseEntity(new ApiResponse(true, "User with id " + userDto.getId() + " was successfully edited"),
                HttpStatus.OK);
    }
}
