package com.example.gaming.repository;

import com.example.gaming.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findById(Long gameId);

    Iterable<Game> findByCreatedBy(Long userId);

    List<Game> findByName(String name);

    List<Game> findByDeveloper(String developer);

    List<Game> findByPrice(Double price);

//    List<Game> findByDateReleased(Date dateReleased);

    List<Game> findByGenre(String genre);

    Boolean existsByName(String name);

    @Query(value = "SELECT * from games WHERE name LIKE CONCAT('%',:keyWord,'%') OR developer LIKE CONCAT('%',:keyWord,'%') OR genre LIKE CONCAT('%',:keyWord,'%')",
            nativeQuery = true)
    List<Game> findAllByKeyWord(String keyWord);
}
