package vn.edu.hcmuaf.fit.cinemix_api.repository.seat;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Seat;

@NoRepositoryBean
public interface SeatRepository extends CustomRepository<Seat, Long> {
}
