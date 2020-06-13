package fr.soat.training.api.superhero.services;

import fr.soat.training.api.superhero.domain.Mission;
import fr.soat.training.api.superhero.domain.SuperHero;
import fr.soat.training.api.superhero.domain.builders.MissionBuilder;
import fr.soat.training.api.superhero.domain.builders.SuperHeroBuilder;
import fr.soat.training.api.superhero.repository.HistoricEventRepository;
import fr.soat.training.api.superhero.repository.MissionRepository;
import fr.soat.training.api.superhero.services.domain.MatchingHero;
import fr.soat.training.api.superhero.services.domain.MatchingMission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MissionServiceShould {

    private MissionService missionService;

    @Mock
    private MissionRepository missionRepository;

    @Mock
    private SuperHeroService superHeroService;

    @Mock
    private HistoricEventRepository historicEventRepository;

    @BeforeEach
    void setUp() {
        this.missionService = new MissionService(missionRepository, superHeroService);
    }

    @Test
    void call_the_findAll_operation_to_get_all_the_missions() {
        this.missionService.getAllTheMissions();

        verify(this.missionRepository).findAll();
    }

    @Test
    void return_a_customised_list_of_mission_when_getting_all_the_missions() {
        SuperHero malicia = new SuperHeroBuilder().createSuperHero("Malicia");
        SuperHero doctorStrange = new SuperHeroBuilder().createSuperHero("Dr. Strange");
        Mission aMission = new MissionBuilder().createMission("To save the X-Men!").assignedTo(malicia).build();
        Mission anotherMission = new MissionBuilder().createMission("Put the levitation mantle").assignedTo(doctorStrange).build();

        when(this.missionRepository.findAll()).thenReturn(Collections.unmodifiableList(Arrays.asList(aMission, anotherMission)));


        List<MatchingMission> allTheMissions = this.missionService.getAllTheMissions();
        assertThat(allTheMissions)
                .as("should return 2 missions")
                .hasSize(2)
                .hasOnlyElementsOfTypes(MatchingMission.class);

    }

    @Test
    void call_the_findById_operation_on_mission_when_getting_a_mission_by_id_is_requested() {
        UUID missionUUID = UUID.randomUUID();
        SuperHero doctorStrange = new SuperHeroBuilder().createSuperHero("Dr. Strange");
        Mission found = new MissionBuilder().createMission("Put the levitation mantle").assignedTo(doctorStrange).build();

        when(this.missionRepository.findById(any(UUID.class))).thenReturn(Optional.of(found));

        this.missionService.getMission(missionUUID);

        verify(this.missionRepository).findById(missionUUID);
    }

    @Test
    void return_a_customised_mission_when_getting_a_mission_by_uuid_is_requested() {
        SuperHero doctorStrange = new SuperHeroBuilder().createSuperHero("Dr. Strange");
        Mission found = new MissionBuilder().createMission("Put the levitation mantle").assignedTo(doctorStrange).build();
        when(this.missionRepository.findById(any(UUID.class))).thenReturn(Optional.of(found));

        MatchingMission mission = this.missionService.getMission(UUID.randomUUID());

        assertThat(mission)
                .isNotNull()
                .as("Should convert the mission entity to a matching hero instance")
                .isInstanceOf(MatchingMission.class);


    }

    @Test
    void call_the_find_the_hero_matching_the_name_when_the_creation_of_a_mission_is_requested() {
        MatchingHero randomHero = new MatchingHero("hero", null);
        when(this.superHeroService.getTheSuperHeroMatching(Mockito.anyString())).thenReturn(randomHero);

        String superHeroName = "Batman";

        this.missionService.createAMissionFor(superHeroName, "random");

        verify(this.superHeroService).getTheSuperHeroMatching(superHeroName);
    }

    @Test
    void call_the_saveAndFlush_operation_on_mission_when_the_creation_of_a_mission_is_requested() {
        MatchingHero hero = new MatchingHero("Wolverine", LocalDateTime.now());
        when(this.superHeroService.getTheSuperHeroMatching(Mockito.anyString())).thenReturn(hero);

        this.missionService.createAMissionFor("Wolverine", "To save Yashida!");

        Mockito.verify(this.missionRepository).saveAndFlush(any(Mission.class));
    }
}
