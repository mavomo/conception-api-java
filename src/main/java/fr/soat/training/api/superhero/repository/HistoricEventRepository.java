package fr.soat.training.api.superhero.repository;

import fr.soat.training.api.superhero.domain.HistoricEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface HistoricEventRepository extends JpaRepository<HistoricEvent, UUID> {

    @Query("SELECT h FROM HistoricEvent AS h WHERE h.mission.uuid = ?1")
    List<HistoricEvent> findAllByMission(UUID missionUuid);
}
