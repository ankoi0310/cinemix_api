package vn.edu.hcmuaf.fit.cinemix_api.repository.approle;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.UserRole;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppRole;

import java.util.Optional;

@NoRepositoryBean
public interface AppRoleRepository extends CustomRepository<AppRole, Long> {
    Optional<AppRole> findByRole(UserRole role);
}
