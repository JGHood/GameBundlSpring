package com.example.gaming.payload;

import com.example.gaming.model.Game;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class BundleRequest {

    @NotBlank
    private String name;

//    @NotBlank
//    private Double price;

    @NotBlank
    private Boolean featured;

//    @NotBlank
//    private Set<Game> games;

    public BundleRequest(String name, Boolean featured) {
        this.name = name;
        this.featured = featured;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Double getPrice() {
//        return price;
//    }
//
//    public void setPrice(Double price) {
//        this.price = price;
//    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

//    public Set<Game> getGames() {
//        return games;
//    }
//
//    public void setGames(Set<Game> games) {
//        this.games = games;
//    }
}
