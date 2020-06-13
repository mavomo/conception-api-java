package fr.soat.training.api.superhero.repository;

import fr.soat.training.api.superhero.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MissionRepository extends JpaRepository<Mission, UUID> {
}
