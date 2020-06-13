package fr.soat.training.api.superhero.services;

import fr.soat.training.api.superhero.domain.HistoricEvent;
import fr.soat.training.api.superhero.domain.Mission;
import fr.soat.training.api.superhero.domain.builders.HistoricEventBuilder;
import fr.soat.training.api.superhero.domain.builders.MissionBuilder;
import fr.soat.training.api.superhero.repository.HistoricEventRepository;
import fr.soat.training.api.superhero.services.domain.MatchingHistoricEvent;
import fr.soat.training.api.superhero.services.domain.MatchingMission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HistoricEventService {

    @Autowired
    private MissionService missionService;

    @Autowired
    private HistoricEventRepository historicEventRepository;

    public HistoricEventService(MissionService missionService, HistoricEventRepository historicEventRepository) {
        this.missionService = missionService;
        this.historicEventRepository = historicEventRepository;
    }

    public void createNewEventOnMission(UUID missionUUID, String actionDesciption) {
        MatchingMission matchingMission = this.missionService.getMission(missionUUID);
        Mission relatedMission = new MissionBuilder().createMission(matchingMission.getTitle()).assignedTo(matchingMission.getAssignedHeroName()).build();
        HistoricEvent theEvent = new HistoricEventBuilder().createAction(actionDesciption).madeDuringTheMission(relatedMission)
                .build();

        this.historicEventRepository.saveAndFlush(theEvent);
    }

    public List<MatchingHistoricEvent> retrieveAllEventsOfAMission(UUID missionId) {
        List<HistoricEvent> historicEvents = this.historicEventRepository.findAllByMission(missionId);
        return historicEvents.stream().map(he -> new MatchingHistoricEvent(he))
                .collect(Collectors.toList());

    }
}
