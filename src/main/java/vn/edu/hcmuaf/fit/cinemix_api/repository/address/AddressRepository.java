package vn.edu.hcmuaf.fit.cinemix_api.repository.address;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Address;

@NoRepositoryBean
public interface AddressRepository extends CustomRepository<Address, Long> {
}
