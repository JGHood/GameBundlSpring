package com.example.gaming.model;

import java.util.Date;

public class GameDto {

    private Long id;
    private String name;
    private String developer;
    private Double price;
    private Date releaseDate;
    private String genre;

    public GameDto(Long id, String name, String developer, Double price, Date releaseDate, String genre) {
        this.id = id;
        this.name = name;
        this.developer = developer;
        this.price = price;
        this.releaseDate = releaseDate;
        this.genre = genre;
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

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
