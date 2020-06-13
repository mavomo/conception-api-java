package fr.soat.training.api.superhero.services;

import fr.soat.training.api.superhero.domain.SuperHero;
import fr.soat.training.api.superhero.domain.builders.SuperHeroBuilder;
import fr.soat.training.api.superhero.repository.SuperHeroRepository;
import fr.soat.training.api.superhero.services.domain.MatchingHero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SuperHeroService {

    @Autowired
    private SuperHeroRepository superHeroRepository;

    public SuperHeroService(SuperHeroRepository superHeroRepository) {
        this.superHeroRepository = superHeroRepository;
    }

    public void createSuperHero(String name) {
        SuperHero toBeSaved = new SuperHeroBuilder().createSuperHero(name);
        this.superHeroRepository.saveAndFlush(toBeSaved);
    }

    public List<MatchingHero> findAllTheMissions() {
        List<SuperHero> missions = this.superHeroRepository.findAll();
        return missions.stream().map(hero -> new MatchingHero(hero)).collect(Collectors.toList());
    }

    public MatchingHero getTheSuperHeroMatching(String name) {
        Optional<SuperHero> matchingSuperHero = this.superHeroRepository.findByName(name);
        return Optional.ofNullable(matchingSuperHero).map(h -> new MatchingHero(h.get()))
                .orElse(null);
    }

    public MatchingHero getSuperHero(UUID heroId) {
        Optional<SuperHero> found = this.superHeroRepository.findById(heroId);
        return Optional.ofNullable(found).map(sp -> new MatchingHero(sp.get())).orElse(null);
    }
}
