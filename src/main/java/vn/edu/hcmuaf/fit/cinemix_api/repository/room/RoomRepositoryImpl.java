package vn.edu.hcmuaf.fit.cinemix_api.repository.room;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Room;

@Repository
public class RoomRepositoryImpl extends AbstractRepository<Room, Long> implements RoomRepository {
    protected RoomRepositoryImpl(EntityManager entityManager) {
        super(Room.class, entityManager);
    }
}
