package com.example.gaming.model;

import com.example.gaming.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
@Table(name = "games", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        })
})
public class Game extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String developer;

    @NotBlank
    private Double price;

    @NotBlank
    private Date releaseDate;

    @NotBlank
    private String genre;

    public Game() {

    }

    public Game(String name, String developer, Double price, Date releaseDate, String genre) {
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
