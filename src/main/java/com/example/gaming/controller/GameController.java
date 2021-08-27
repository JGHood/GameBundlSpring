package com.example.gaming.controller;

import com.example.gaming.model.Game;
import com.example.gaming.model.GameDto;
import com.example.gaming.payload.ApiResponse;
import com.example.gaming.payload.GameRequest;
import com.example.gaming.repository.GameRepository;
import com.example.gaming.repository.UserRepository;
import com.example.gaming.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    GameService gameService;

    // create a game
    @PostMapping("/create")
    public ResponseEntity<?> createGame(@Valid @RequestBody GameRequest gameRequest) {
        return gameService.createGame(gameRequest);
    }

    // get a game by id
    @GetMapping("/getGameById")
    public @ResponseBody GameDto getGameById(@RequestParam(value = "gameId") Long gameId) {
        return gameService.getGameById(gameId);
    }

    // get all games
    @GetMapping("/getAllGames")
    public @ResponseBody Iterable<GameDto> getAllGames() {
        return gameService.getAllGames();
    }

    // search games table for keyword present anywhere
    @GetMapping("/getGamesByKeyWord")
    public @ResponseBody Iterable<GameDto> getGamesByKeyWord(@RequestParam(value = "keyWord") String keyWord) {
        return gameService.getGameByKeyWord(keyWord);
    }

    @PostMapping("/editGame")
    public ResponseEntity<?> editGame(@Valid @RequestBody GameDto gameDto) {
        return gameService.editGame(gameDto);
    }
}
