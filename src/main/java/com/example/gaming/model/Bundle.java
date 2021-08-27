package com.example.gaming.model;

import com.example.gaming.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bundles", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        })
})
public class Bundle extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private Double price;

    @NotBlank
    private Boolean featured;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "bundle_games",
            joinColumns = @JoinColumn(name = "bundle_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private Set<Game> games = new HashSet<>();

    @NotBlank
    private Double discountRate;

    public Bundle() {

    }

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "user_bundles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "bundle_id"))
//    private Set<User> users = new HashSet<>();


//    public Bundle(Long id, String name, Double price, Boolean featured, Set<Game> games) {
//        this.name = name;
//        this.price = price;
//        this.featured = featured;
//        this.games = games;
//    }

    public Bundle(String name, Boolean featured) {
        this.name = name;
        this.featured = featured;
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

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }
}
