package com.example.gaming.service;

import com.example.gaming.model.Bundle;
import com.example.gaming.model.BundleDto;
import com.example.gaming.model.Game;
import com.example.gaming.model.GameDto;
import com.example.gaming.payload.ApiResponse;
import com.example.gaming.payload.BundleRequest;
import com.example.gaming.repository.BundleRepository;
import com.example.gaming.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
public class BundleService {

    @Autowired
    BundleRepository bundleRepository;

    @Autowired
    GameRepository gameRepository;

    public ResponseEntity<?> createBundle(BundleRequest bundleRequest) {
        if(bundleRepository.existsByName(bundleRequest.getName())) {
            return new ResponseEntity(new ApiResponse(false, "Bundle with this name already exists!"),
                    HttpStatus.BAD_REQUEST);
        }

        Bundle bundle = new Bundle(bundleRequest.getName(), bundleRequest.getFeatured());

        bundleRepository.save(bundle);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{bundleId}")
                .buildAndExpand(bundle.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Bundle Created Successfully" + bundle.getId()));
    }

    public ResponseEntity<?> addGame(Long bundleId, List<Long> gameIds) {
        Optional<Bundle> bundle = bundleRepository.findById(bundleId);
        List<Game> games = new ArrayList<>();
        List<Double> gamePrices = new ArrayList<>();

        for (Long gameId : gameIds) {
            Optional<Game> game = gameRepository.findById(gameId);
            games.add(game.get());
            gamePrices.add(game.get().getPrice());
        }

        bundle.get().getGames().addAll(games);

        Map<Double, Double> discountAndPrice = getDiscount(gamePrices);

        bundle.get().setDiscountRate(discountAndPrice.entrySet().iterator().next().getKey());
        bundle.get().setPrice(discountAndPrice.entrySet().iterator().next().getValue());

        bundleRepository.save(bundle.get());

        return new ResponseEntity(new ApiResponse(true, "Game(s) successfully added to bundle"),
                HttpStatus.OK);
    }

    public BundleDto getBundleById(Long bundleId) {
        Bundle bundle = bundleRepository.findById(bundleId).get();


        BundleDto bundleDto = new BundleDto(bundle.getId(), bundle.getName(), bundle.getPrice(),
                bundle.getFeatured(), new HashSet<GameDto>((Collection)getGamesByBundle(bundleId)), bundle.getDiscountRate());

        return bundleDto;
    }

    public Iterable<GameDto> getGamesByBundle(Long bundleId) {
        Bundle bundle = bundleRepository.findById(bundleId).get();
        ArrayList<GameDto> gameDtos = new ArrayList<>();

        for (Game game : bundle.getGames()) {
            GameDto gameDto = new GameDto(game.getId(), game.getName(), game.getDeveloper(), game.getPrice(),
                    game.getReleaseDate(), game.getGenre());

            gameDtos.add(gameDto);
        }

        return gameDtos;
    }

    public Iterable<BundleDto> getAllBundles() {
        ArrayList<BundleDto> bundleDtos = new ArrayList<>();

        for (Bundle bundle : bundleRepository.findAll()) {
            Set<GameDto> gameDtos = new HashSet<>();

            for (Game game : bundle.getGames()) {
                GameDto gameDto = new GameDto(game.getId(), game.getName(), game.getDeveloper(), game.getPrice(),
                        game.getReleaseDate(), game.getGenre());

                gameDtos.add(gameDto);
            }
            BundleDto bundleDto = new BundleDto(bundle.getId(), bundle.getName(), bundle.getPrice(),
                    bundle.getFeatured(), gameDtos, bundle.getDiscountRate());

            bundleDtos.add(bundleDto);
        }

        return bundleDtos;
    }

    public Iterable<BundleDto> getFeaturedBundles() {
        ArrayList<BundleDto> bundleDtos = new ArrayList<>();

        for (Bundle bundle : bundleRepository.findByFeatured(true)) {
            Set<GameDto> gameDtos = new HashSet<>();

            for (Game game : bundle.getGames()) {
                GameDto gameDto = new GameDto(game.getId(), game.getName(), game.getDeveloper(), game.getPrice(),
                        game.getReleaseDate(), game.getGenre());

                gameDtos.add(gameDto);
            }
            BundleDto bundleDto = new BundleDto(bundle.getId(), bundle.getName(), bundle.getPrice(),
                    bundle.getFeatured(), gameDtos, bundle.getDiscountRate());

            bundleDtos.add(bundleDto);
        }

        return bundleDtos;
    }

    public Iterable<BundleDto> getEmployeeCreatedBundles() {
        ArrayList<BundleDto> bundleDtos = new ArrayList<>();

        for (Bundle bundle : bundleRepository.findBundlesCreatedByEmployees()) {
            Set<GameDto> gameDtos = new HashSet<>();

            for (Game game : bundle.getGames()) {
                GameDto gameDto = new GameDto(game.getId(), game.getName(), game.getDeveloper(), game.getPrice(),
                        game.getReleaseDate(), game.getGenre());

                gameDtos.add(gameDto);
            }
            BundleDto bundleDto = new BundleDto(bundle.getId(), bundle.getName(), bundle.getPrice(),
                    bundle.getFeatured(), gameDtos, bundle.getDiscountRate());

            bundleDtos.add(bundleDto);
        }

        return bundleDtos;
    }

    public ResponseEntity<?> editBundle(BundleDto bundleDto) {
        Bundle bundle = bundleRepository.findById(bundleDto.getId()).get();

        bundle.setName(bundleDto.getName() == null ? bundle.getName() : bundleDto.getName());
        bundle.setPrice(bundleDto.getPrice() == null ? bundle.getPrice() : bundleDto.getPrice());
        bundle.setFeatured(bundleDto.getFeatured() == null ? bundle.getFeatured() : bundleDto.getFeatured());

        Set<Game> games = new HashSet<>();
        List<Double> gamePrices = new ArrayList<>();
        if (bundleDto.getGames() != null) {
            for (GameDto gameDto : bundleDto.getGames()) {
                Optional<Game> game = gameRepository.findById(gameDto.getId());
                games.add(game.get());
                gamePrices.add(game.get().getPrice());
            }
        }

        bundle.setGames(bundleDto.getGames() == null ? bundle.getGames() : games);

        Map<Double, Double> discountAndPrice = getDiscount(gamePrices);

        bundle.setDiscountRate(discountAndPrice.entrySet().iterator().next().getKey());
        bundle.setPrice(discountAndPrice.entrySet().iterator().next().getValue());

        bundleRepository.save(bundle);

        return new ResponseEntity(new ApiResponse(true, "Bundle with id " + bundleDto.getId() + " was successfully edited"),
                HttpStatus.OK);
    }

    public Map<Double, Double> getDiscount(List<Double> gamePrices) {
        Double totalDiscount = 0.0;
        Double discountedPrice = 0.0;
        List<Double> tempTotalDiscountOptions = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Double tempTotalDiscount = 0.0;

            for (Double gamePrice : gamePrices) {
                Double x = 0.0;
                if (gamePrice < 20.00) {
                    x = (Math.random() * ((4 - 1) + 1)) + 1;
                } else if (gamePrice > 20.00 && gamePrice < 40.00) {
                    x = (Math.random() * ((9 - 4) + 1)) + 4;
                } else if (gamePrice > 40.00) {
                    x = (Math.random() * ((12 - 5) + 1)) + 5;
                }
                System.out.println("Discount x " + x);
                tempTotalDiscount += x;
                System.out.println("tempTotalDiscount " + tempTotalDiscount);
            }
            tempTotalDiscountOptions.add(tempTotalDiscount);
        }

        totalDiscount = tempTotalDiscountOptions.get((int)(Math.random() * ((3 - 1) + 1)) + 1);

        Double originalPrice = gamePrices.stream().mapToDouble(a -> a).sum();
        System.out.println("originalPrice " + originalPrice);
        discountedPrice = originalPrice - (originalPrice * (totalDiscount/100));
        System.out.println("discountedPrice " + discountedPrice);


        Map<Double, Double> returnValue = new HashMap<>();
        returnValue.put(Math.round(totalDiscount * 100.0) / 100.0, Math.round(discountedPrice * 100.0) / 100.0);
        return returnValue;
    }
}
