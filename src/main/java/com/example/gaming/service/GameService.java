package com.example.gaming.service;

import com.example.gaming.model.Game;
import com.example.gaming.model.GameDto;
import com.example.gaming.model.User;
import com.example.gaming.payload.ApiResponse;
import com.example.gaming.payload.GameRequest;
import com.example.gaming.repository.GameRepository;
import com.example.gaming.repository.UserRepository;
import com.example.gaming.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Array;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class GameService {
    @Autowired
    GameRepository gameRepository;

    public ResponseEntity<?> createGame(GameRequest gameRequest) {
        if(gameRepository.existsByName(gameRequest.getName())) {
            return new ResponseEntity(new ApiResponse(false, "Game with this name already exists!"),
                    HttpStatus.BAD_REQUEST);
        }
        String dateString = gameRequest.getReleaseDate();

        Date date = new Date();
        try {
            date = new SimpleDateFormat("MMM d yyyy").parse(dateString);
        } catch (ParseException e) {

        }
        Game game = new Game(gameRequest.getName(), gameRequest.getDeveloper(), gameRequest.getPrice(),
                date, gameRequest.getGenre());

        gameRepository.save(game);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{gameId}")
                .buildAndExpand(game.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Game Created Successfully"));
    }

    public GameDto getGameById(Long gameId) {
        Game game = gameRepository.findById(gameId).get();

        GameDto gameDto = new GameDto(game.getId(), game.getName(), game.getDeveloper(), game.getPrice(), game.getReleaseDate(), game.getGenre());

        return gameDto;
    }

    public Iterable<GameDto> getAllGames() {
        ArrayList<GameDto> gameDtos = new ArrayList<>();

        for (Game game : gameRepository.findAll()) {
            GameDto gameDto = new GameDto(game.getId(), game.getName(), game.getDeveloper(), game.getPrice(),
                    game.getReleaseDate(), game.getGenre());

            gameDtos.add(gameDto);
        }
        return gameDtos;
    }

    public Iterable<GameDto> getGameByKeyWord(String keyWord) {
        ArrayList<GameDto> gameDtos = new ArrayList<>();

        for (Game game : gameRepository.findAllByKeyWord(keyWord)) {
            GameDto gameDto = new GameDto(game.getId(), game.getName(), game.getDeveloper(), game.getPrice(),
                    game.getReleaseDate(), game.getGenre());

            gameDtos.add(gameDto);
        }
        return gameDtos;
    }

    public ResponseEntity<?> editGame(GameDto gameDto) {
        Game game = gameRepository.findById(gameDto.getId()).get();

        game.setName(gameDto.getName() == null ? game.getName() : gameDto.getName());
        game.setDeveloper(gameDto.getDeveloper() == null ? game.getDeveloper() : gameDto.getDeveloper());
        game.setPrice(gameDto.getPrice() == null ? game.getPrice() : gameDto.getPrice());
        game.setReleaseDate(gameDto.getReleaseDate() == null ? game.getReleaseDate() : gameDto.getReleaseDate());
        game.setGenre(gameDto.getGenre() == null ? game.getGenre() : gameDto.getGenre());

        gameRepository.save(game);

        return new ResponseEntity(new ApiResponse(true, "Game with id " + gameDto.getId() + " was successfully edited"),
                HttpStatus.OK);
    }
}
