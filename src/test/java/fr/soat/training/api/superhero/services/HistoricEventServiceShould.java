package fr.soat.training.api.superhero.services;

import fr.soat.training.api.superhero.domain.HistoricEvent;
import fr.soat.training.api.superhero.domain.Mission;
import fr.soat.training.api.superhero.domain.SuperHero;
import fr.soat.training.api.superhero.domain.builders.HistoricEventBuilder;
import fr.soat.training.api.superhero.domain.builders.MissionBuilder;
import fr.soat.training.api.superhero.domain.builders.SuperHeroBuilder;
import fr.soat.training.api.superhero.repository.HistoricEventRepository;
import fr.soat.training.api.superhero.services.domain.MatchingHistoricEvent;
import fr.soat.training.api.superhero.services.domain.MatchingMission;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HistoricEventServiceShould {
    private HistoricEventService historicEventService;

    @Mock
    private MissionService missionService;

    @Mock
    private HistoricEventRepository historicEventRepository;

    @BeforeEach
    void setUp() {
        this.historicEventService = new HistoricEventService(missionService, historicEventRepository);
    }

    @Test
    void call_the_getMission_and_saveAndFlush_operations_when_the_creation_of_an_historical_event_is_requested() {
        UUID fakeMissionId = UUID.randomUUID();
        SuperHero malicia = new SuperHeroBuilder().createSuperHero("malicia");
        Mission aMission = new MissionBuilder().createMission("To save the X-Men!").assignedTo(malicia).build();
        MatchingMission matchingMission = new MatchingMission(aMission);
        when(missionService.getMission(any(UUID.class))).thenReturn(matchingMission);

        historicEventService.createNewEventOnMission(fakeMissionId, "Save the world");

        verify(missionService).getMission(fakeMissionId);
        verify(historicEventRepository).saveAndFlush(Mockito.any(HistoricEvent.class));
    }

    @Test
    void call_findAllByMission_operation_to_get_all_the_historic_events_for_a_mission() {
        Mission aMission = new MissionBuilder().createMission("mission !").assignedTo("ze hero").build();

        HistoricEvent firstEvent = new HistoricEventBuilder().createAction("first action").madeDuringTheMission(aMission).build();

        HistoricEvent secondEvent = new HistoricEventBuilder().createAction("another action").madeDuringTheMission(aMission).build();
        HistoricEvent finalEvent = new HistoricEventBuilder().createAction("final action!!!").madeDuringTheMission(aMission).build();

        when(historicEventRepository.findAllByMission(any(UUID.class)))
                .thenReturn(Collections.unmodifiableList(Arrays.asList(firstEvent, secondEvent, finalEvent)));

        UUID fakeMissionId = UUID.randomUUID();
        List<MatchingHistoricEvent> historicEvents = this.historicEventService.retrieveAllEventsOfAMission(fakeMissionId);

        verify(historicEventRepository).findAllByMission(fakeMissionId);

        Assertions.assertThat(historicEvents)
                .as("should have 3 entries of type MatchingHistoricEvent")
                .hasSize(3)
                .hasOnlyElementsOfTypes(MatchingHistoricEvent.class);
    }
}
