package com.example.gaming.service;

import com.example.gaming.model.GameDto;
import com.example.gaming.payload.ApiResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameServiceTests {

    @Autowired
    GameService gameService;

    public String keyWord;
    public Long gameId;

    @BeforeAll
    public void init() {
        keyWord = "all";

        gameId = 1L;
    }

    @Test
    public void testGetGameById() {
        GameDto gameDto = gameService.getGameById(gameId);

        assertNotNull(gameDto);
    }

    @Test
    public void testGetAllGames() {
        Iterable<GameDto> gameDtos = gameService.getAllGames();

        assertTrue(gameDtos.iterator().hasNext());
    }

    @Test
    public void testGetGamesByKeyWord() {
        Iterable<GameDto> gameDtos = gameService.getGameByKeyWord(keyWord);

        assertTrue(gameDtos.iterator().hasNext());
    }

    @Test
    public void testEditGame() {
        GameDto gameDto = gameService.getGameById(gameId);

        GameDto tempGameDto = new GameDto(gameDto.getId(), gameDto.getName(), gameDto.getDeveloper(),
                gameDto.getPrice(), gameDto.getReleaseDate(), gameDto.getGenre());

        tempGameDto.setName("tempName");
        tempGameDto.setDeveloper("tempDeveloper");
        tempGameDto.setGenre("tempGenre");

        ResponseEntity<?> responseEntity = gameService.editGame(tempGameDto);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(apiResponse.getSuccess());
        assertEquals("Game with id " + gameDto.getId() + " was successfully edited", apiResponse.getMessage());

        ResponseEntity<?> responseEntity2 = gameService.editGame(gameDto);
        ApiResponse apiResponse2 = (ApiResponse) responseEntity2.getBody();

        assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());
        assertTrue(apiResponse2.getSuccess());
        assertEquals("Game with id " + gameDto.getId() + " was successfully edited", apiResponse2.getMessage());
    }
}
