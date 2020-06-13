package fr.soat.training.api.superhero.repository;

import fr.soat.training.api.superhero.BaseRepositoryTest;
import fr.soat.training.api.superhero.domain.SuperHero;
import fr.soat.training.api.superhero.domain.builders.SuperHeroBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

public class SuperHeroRepositoryShould extends BaseRepositoryTest {

    private final SuperHero batman = new SuperHeroBuilder().createSuperHero("Batman");

    @Test
    void save_a_super_hero_given_a_name() {

        SuperHero savedHero = superHeroRepository.saveAndFlush(batman);

        Assertions.assertThat(savedHero).as("the hero should have been persisted")
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("name", "Batman");
    }

    @Test
    void retrieve_a_super_hero_using_its_uuid() {
        SuperHero savedHero = superHeroRepository.saveAndFlush(batman);

        Optional<SuperHero> found = superHeroRepository.findById(savedHero.getUUID());

        Assertions.assertThat(found).as("should have returned the previously persisted hero").isPresent();
        Assertions.assertThat(found).isPresent().get()
                .as("The retrieved hero is different from the target")
                .isEqualToComparingOnlyGivenFields(savedHero, "name","uuid");
    }

    @Test
    void retrieve_a_super_hero_given_a_name() {
        superHeroRepository.saveAndFlush(batman);

        Optional<SuperHero> found = superHeroRepository.findByName("Batman");
        Assertions.assertThat(found)
                .as("Batman exists. the operation findByName should return 1 result")
                .isPresent();
    }

    @Test
    void return_nothing_given_an_unknown_superhero_name() {
        superHeroRepository.saveAndFlush(batman);

        Optional<SuperHero> found = superHeroRepository.findByName("Superman");

        Assertions.assertThat(found)
                .as("A super hero with name *Superman* does not exist yet.")
                .isEmpty();
    }

    @Test
    void fail_to_register_a_new_hero_with_the_same_name() {

         superHeroRepository.saveAndFlush(batman);

        Assertions.assertThatThrownBy(() -> superHeroRepository.saveAndFlush(new SuperHeroBuilder().createSuperHero("Batman")))
                .hasMessageStartingWith("could not execute statement;")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

}
