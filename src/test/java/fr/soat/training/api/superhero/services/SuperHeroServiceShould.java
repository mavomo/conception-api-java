package fr.soat.training.api.superhero.services;

import fr.soat.training.api.superhero.domain.SuperHero;
import fr.soat.training.api.superhero.domain.builders.SuperHeroBuilder;
import fr.soat.training.api.superhero.repository.SuperHeroRepository;
import fr.soat.training.api.superhero.services.domain.MatchingHero;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SuperHeroServiceShould {

    private SuperHeroService superHeroService;

    @Mock
    private SuperHeroRepository superHeroRepository;

    @BeforeEach
    void setUp() {
        superHeroService = new SuperHeroService(this.superHeroRepository);
    }

    @Test
    void call_the_save_and_flush_operation_when_adding_a_new_super_hero() {
        this.superHeroService.createSuperHero("Batman");

        verify(superHeroRepository).saveAndFlush(Mockito.any(SuperHero.class));
    }

    @Test
    void call_the_findAll_operation_when_listing_all_the_existing_missions() {
        this.superHeroService.findAllTheMissions();
        verify(superHeroRepository).findAll();
    }

    @Test
    void return_a_customised_list_of_the_existing_super_heroes() {
        SuperHero batman = new SuperHeroBuilder().createSuperHero("Batman");
        SuperHero malicia = new SuperHeroBuilder().createSuperHero("Malicia");

        Mockito.when(superHeroRepository.findAll()).thenReturn(Collections.unmodifiableList(Arrays.asList(batman, malicia)));

        List<MatchingHero> missions = this.superHeroService.findAllTheMissions();
        Assertions.assertThat(missions)
                .as("Should not return the entities ")
                .hasSize(2)
                .hasOnlyElementsOfType(MatchingHero.class);
    }

    @Test
    void call_the_findByName_operation_to_find_a_superhero_given_a_name() {
        SuperHero wolverine = new SuperHeroBuilder().createSuperHero("Wolverine");
        Mockito.when(this.superHeroRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(wolverine));

        this.superHeroService.getTheSuperHeroMatching("Wolverine");

        verify(this.superHeroRepository).findByName(Mockito.anyString());
    }

    @Test
    void return_an_instance_of_superHeroResponse_when_a_supero_is_was_found_by_its_name() {
        SuperHero wolverine = new SuperHeroBuilder().createSuperHero("Wolverine");
        Mockito.when(this.superHeroRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(wolverine));

        MatchingHero found = this.superHeroService.getTheSuperHeroMatching("Wolverine");

        Assertions.assertThat(found)
                .as("Should not be the entity")
                .isInstanceOf(MatchingHero.class);

    }

    @Test
    void call_the_findById_operation_to_find_a_superhero_given_its_uuid() {
        SuperHero wolverine = new SuperHeroBuilder().createSuperHero("Wolverine");
        Mockito.when(this.superHeroRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(wolverine));

        UUID heroId = UUID.randomUUID();
        this.superHeroService.getSuperHero(heroId);

        verify(this.superHeroRepository).findById(heroId);
    }
}
