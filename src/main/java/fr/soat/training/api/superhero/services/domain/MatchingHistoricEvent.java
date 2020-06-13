package fr.soat.training.api.superhero.services.domain;

import fr.soat.training.api.superhero.domain.HistoricEvent;
import fr.soat.training.api.superhero.domain.Mission;

import java.time.LocalDateTime;

public class MatchingHistoricEvent {
    private Mission mission;
    private String description;
    private LocalDateTime createdAt;

    public MatchingHistoricEvent(HistoricEvent event) {
        this(event.getMission(), event.getDescription(), event.getCreatedAt());
    }

    public MatchingHistoricEvent(Mission mission, String description, LocalDateTime createdAt) {
        this.mission = mission;
        this.description = description;
        this.createdAt = createdAt;
    }


}
