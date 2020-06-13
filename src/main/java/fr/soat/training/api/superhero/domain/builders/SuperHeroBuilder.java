package fr.soat.training.api.superhero.domain.builders;

import fr.soat.training.api.superhero.domain.SuperHero;

public class SuperHeroBuilder {

    public SuperHero createSuperHero(String name) {
        SuperHero hero = new SuperHero(name);
        return hero;
    }
}
