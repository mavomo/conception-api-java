package fr.soat.training.api.superhero.repository;

import fr.soat.training.api.superhero.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, String> {
}
