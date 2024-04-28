package vn.edu.hcmuaf.fit.cinemix_api.repository.room;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Room;

@NoRepositoryBean
public interface RoomRepository extends CustomRepository<Room, Long> {
}
