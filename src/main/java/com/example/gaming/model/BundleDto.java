package com.example.gaming.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BundleDto {

    private Long id;
    private String name;
    private Double price;
    private Boolean featured;
    private Set<GameDto> games;
    private Double discountRate;

    public BundleDto() {

    }

    public BundleDto(Long id, String name, Double price, Boolean featured, Set<GameDto> games, Double discountRate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.featured = featured;
        this.games = games;
        this.discountRate = discountRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Set<GameDto> getGames() {
        return games;
    }

    public void setGames(Set<GameDto> games) {
        this.games = games;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }
}
