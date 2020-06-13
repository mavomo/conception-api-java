package fr.soat.training.api.superhero.services;

import fr.soat.training.api.superhero.domain.Mission;
import fr.soat.training.api.superhero.domain.builders.MissionBuilder;
import fr.soat.training.api.superhero.repository.HistoricEventRepository;
import fr.soat.training.api.superhero.repository.MissionRepository;
import fr.soat.training.api.superhero.services.domain.MatchingHero;
import fr.soat.training.api.superhero.services.domain.MatchingMission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MissionService {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private SuperHeroService superHeroService;

    public MissionService(MissionRepository missionRepository, SuperHeroService superHeroService) {
        this.missionRepository = missionRepository;
        this.superHeroService = superHeroService;
    }

    public List<MatchingMission> getAllTheMissions() {

        List<Mission> missions = this.missionRepository.findAll();

        return missions.stream()
                .map(mission -> new MatchingMission(mission))
                .collect(Collectors.toList());
    }

    public void createAMissionFor(String superHeroName, String missionTitle) {
        MatchingHero matchingHero = this.superHeroService.getTheSuperHeroMatching(superHeroName);
        Mission theMission = new MissionBuilder().createMission(missionTitle).assignedTo(matchingHero.getName()).build();

        this.missionRepository.saveAndFlush(theMission);
    }

    public MatchingMission getMission(UUID missionUUID) {
        Optional<Mission> mission = this.missionRepository.findById(missionUUID);
        return Optional.ofNullable(mission).map(m -> new MatchingMission(m.get())).orElse(null);
    }
}
