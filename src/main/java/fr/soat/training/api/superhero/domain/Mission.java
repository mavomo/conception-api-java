package fr.soat.training.api.superhero.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "MISSION")
public class Mission implements Serializable {

    @Id
    @Column(name = "UUID")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID uuid;

    @Column(name = "TITLE", nullable = false, updatable = false)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ASSIGNED_HERO_UUID", nullable = false, updatable = false)
    private SuperHero assignedHero;

    @Column(name = "CREATION_DATE", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Mission() {
        //left blank to make JPA happy
    }

    public Mission(String title){
        this.title = title;
        this.createdAt = LocalDateTime.now();
    }

    public void setAssignedHero(SuperHero assignedHero) {
        this.assignedHero = assignedHero;
    }

    public SuperHero getAssignedHero() {
        return assignedHero;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public String getTitle() {
        return this.title;
    }

    public String getHeroName() {
        return this.assignedHero.getName();
    }

    public LocalDate getCreateAt() {
        return this.createdAt.toLocalDate();
    }
}
