package vn.edu.hcmuaf.fit.cinemix_api.repository.theater;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Theater;

@NoRepositoryBean
public interface TheaterRepository extends CustomRepository<Theater, Long> {
}
