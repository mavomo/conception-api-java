package fr.soat.training.api.superhero.repository;

import fr.soat.training.api.superhero.domain.SuperHero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SuperHeroRepository  extends JpaRepository<SuperHero, String> {
    Optional<SuperHero> findByName(String name);
}
