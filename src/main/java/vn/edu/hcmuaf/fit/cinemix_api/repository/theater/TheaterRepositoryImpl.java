package vn.edu.hcmuaf.fit.cinemix_api.repository.theater;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.QTheater;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Theater;

@Repository
public class TheaterRepositoryImpl extends AbstractRepository<Theater, Long> implements TheaterRepository {
    private final QTheater qTheater = QTheater.theater;

    protected TheaterRepositoryImpl(EntityManager entityManager) {
        super(Theater.class, entityManager);
    }
}
