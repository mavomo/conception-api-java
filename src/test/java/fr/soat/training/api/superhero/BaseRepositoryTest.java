package fr.soat.training.api.superhero;

import fr.soat.training.api.superhero.repository.HistoricEventRepository;
import fr.soat.training.api.superhero.repository.MissionRepository;
import fr.soat.training.api.superhero.repository.SuperHeroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class BaseRepositoryTest {

    @Autowired
    protected SuperHeroRepository superHeroRepository;

    @Autowired
    protected MissionRepository missionRepository;

    @Autowired
    protected HistoricEventRepository historicEventRepository;

    @BeforeEach
    void setUp() {
        this.historicEventRepository.deleteAllInBatch();
        this.missionRepository.deleteAllInBatch();
        this.superHeroRepository.deleteAllInBatch();
    }
}
