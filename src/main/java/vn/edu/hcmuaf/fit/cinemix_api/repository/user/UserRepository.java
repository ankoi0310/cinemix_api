package vn.edu.hcmuaf.fit.cinemix_api.repository.user;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppUser;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepository extends CustomRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String value);

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByPhone(String phone);
}
