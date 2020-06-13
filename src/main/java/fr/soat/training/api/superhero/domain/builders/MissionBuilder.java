package fr.soat.training.api.superhero.domain.builders;

import fr.soat.training.api.superhero.domain.Mission;
import fr.soat.training.api.superhero.domain.SuperHero;
import fr.soat.training.api.superhero.services.domain.MatchingHero;

public class MissionBuilder {
    private Mission mission;

    public MissionBuilder createMission(String missionName) {
        this.mission = new Mission(missionName);
        return this;
    }

    public MissionBuilder assignedTo(SuperHero superman) {
        if (this.mission == null) {
            throw new IllegalArgumentException("the mission is mandataroy");
        }
        this.mission.setAssignedHero(superman);

        return this;
    }

    public MissionBuilder assignedTo(String heroName) {
        if (this.mission == null) {
            throw new IllegalArgumentException("the mission is mandataroy");
        }
        SuperHero hero = new SuperHero(heroName);
        this.mission.setAssignedHero(hero);

        return this;
    }

    public Mission build() {
        if (this.mission == null) {
            throw new IllegalArgumentException("the mission is mandataroy");
        }

        if (this.mission.getAssignedHero() == null) {
            throw new IllegalArgumentException(" the hero is mandataroy");
        }
        return this.mission;
    }

}
