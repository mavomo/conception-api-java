package fr.soat.training.api.superhero.services.domain;

import fr.soat.training.api.superhero.domain.SuperHero;

import java.time.LocalDateTime;

public class MatchingHero {
    private String name;
    private LocalDateTime createdSince;

    public MatchingHero(SuperHero superHero) {
        this(superHero.getName(), superHero.getCreatedAt());
    }

    public MatchingHero(String name, LocalDateTime createdSince) {
        this.name = name;
        this.createdSince = createdSince;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedSince() {
        return createdSince;
    }
}
