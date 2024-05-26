package vn.edu.hcmuaf.fit.cinemix_api.repository.seat;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Seat;

@Repository
public class SeatRepositoryImpl extends AbstractRepository<Seat, Long> implements SeatRepository {
    protected SeatRepositoryImpl(EntityManager entityManager) {
        super(Seat.class, entityManager);
    }
}
