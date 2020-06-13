package fr.soat.training.api.superhero.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "HISTORIC_EVENT")
public class HistoricEvent implements Serializable {
    @Id
    @Column(name = "UUID")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID uuid;

    @Column(name = "DESCRIPTION", nullable = false, updatable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MISSION_UUID", nullable = false, updatable = false)
    private Mission mission;

    @Column(name = "CREATION_DATE", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public HistoricEvent() { }

    public HistoricEvent(String description) {
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    public void addMission(Mission aMission) {
        this.mission = aMission;
    }

    public String getDescription() {
        return description;
    }

    public Mission getMission() {
        return this.mission;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}
