package fr.soat.training.api.superhero.domain.builders;

import fr.soat.training.api.superhero.domain.HistoricEvent;
import fr.soat.training.api.superhero.domain.Mission;

import java.io.Serializable;

public class HistoricEventBuilder implements Serializable {

    private HistoricEvent historicEvent;

    public HistoricEventBuilder createAction(String description) {
        historicEvent = new HistoricEvent(description);
        return this;
    }

    public HistoricEventBuilder madeDuringTheMission(Mission aMission) {
        historicEvent.addMission(aMission);
        return this;
    }

    public HistoricEvent build() {
        if(this.historicEvent.getDescription() == null){
            throw new IllegalArgumentException("The historic event description is missing");
        }
        if(this.historicEvent.getMission() == null){
            throw new IllegalArgumentException("The mission for which the historic event occured is missing");
        }
        return this.historicEvent;
    }
}
