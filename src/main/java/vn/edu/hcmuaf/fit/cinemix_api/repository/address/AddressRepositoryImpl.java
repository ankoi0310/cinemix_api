package vn.edu.hcmuaf.fit.cinemix_api.repository.address;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Address;

@Repository
public class AddressRepositoryImpl extends AbstractRepository<Address, Long> implements AddressRepository {
    protected AddressRepositoryImpl(EntityManager entityManager) {
        super(Address.class, entityManager);
    }
}
