package fr.soat.training.api.superhero.services.domain;

import fr.soat.training.api.superhero.domain.Mission;

import java.io.Serializable;
import java.time.LocalDate;

public class MatchingMission implements Serializable {

    private String title;
    private String assignedHeroName;
    private LocalDate createdAt;

    public MatchingMission(Mission mission) {
        this(mission.getTitle(), mission.getHeroName(), mission.getCreateAt());
    }

    public MatchingMission(String title, String assignedHeroName, LocalDate createdAt) {
        this.title = title;
        this.assignedHeroName = assignedHeroName;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public String getAssignedHeroName() {
        return assignedHeroName;
    }
}
