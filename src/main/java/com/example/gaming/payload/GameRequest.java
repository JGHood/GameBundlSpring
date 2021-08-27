package com.example.gaming.payload;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class GameRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String developer;

    @NotBlank
    private Double price;

    @NotBlank
    private String releaseDate;

    @NotBlank
    private String genre;

    public GameRequest(String name, String developer, Double price, String releaseDate, String genre) {
        this.name = name;
        this.developer = developer;
        this.price = price;
        this.releaseDate = releaseDate;
        this.genre = genre;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
