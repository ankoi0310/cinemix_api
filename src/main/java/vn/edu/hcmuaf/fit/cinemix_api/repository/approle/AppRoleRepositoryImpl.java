package vn.edu.hcmuaf.fit.cinemix_api.repository.approle;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.UserRole;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppRole;
import vn.edu.hcmuaf.fit.cinemix_api.entity.QAppRole;

import java.util.Optional;

@Repository
public class AppRoleRepositoryImpl extends AbstractRepository<AppRole, Long> implements AppRoleRepository {
    private final QAppRole qAppRole = QAppRole.appRole;

    protected AppRoleRepositoryImpl(EntityManager entityManager) {
        super(AppRole.class, entityManager);
    }

    @Override
    public Optional<AppRole> findByRole(UserRole role) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qAppRole)
                .where(qAppRole.role.eq(role))
                .fetchOne()
        );
    }
}
