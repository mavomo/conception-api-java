package fr.soat.training.api.superhero.repository;

import fr.soat.training.api.superhero.BaseRepositoryTest;
import fr.soat.training.api.superhero.domain.Mission;
import fr.soat.training.api.superhero.domain.SuperHero;
import fr.soat.training.api.superhero.domain.builders.MissionBuilder;
import fr.soat.training.api.superhero.domain.builders.SuperHeroBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class MissionRepositoryShould extends BaseRepositoryTest {
    private final SuperHero superman = new SuperHeroBuilder().createSuperHero("Superman");

    @Test
    void create_a_new_mission_assigned_to_a_superhero() {
        SuperHero heroSaved = superHeroRepository.saveAndFlush(superman);
        Mission firstMission = new MissionBuilder()
                .createMission("Save the world!!").assignedTo(superman).build();

        Mission worldToSave = missionRepository.saveAndFlush(firstMission);

        Assertions.assertThat(worldToSave)
                .as("The fist mission *save the word* should have been created")
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("title", "Save the world!!");
    }

    @Test
    void retrieve_a_mission_using_its_uuid() {
        SuperHero savedSuperHero = superHeroRepository.saveAndFlush(superman);

        Mission secondMission = new MissionBuilder()
                .createMission("Save the world again!!").assignedTo(savedSuperHero).build();

        Mission mission = missionRepository.saveAndFlush(secondMission);

        Optional<Mission> found = missionRepository.findById(mission.getUUID());

        Assertions.assertThat(found)
                .as("should find a missionn based on its uuid")
                .isPresent();
    }
}
