package vn.edu.hcmuaf.fit.cinemix_api.repository.user;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppUser;
import vn.edu.hcmuaf.fit.cinemix_api.entity.QAppUser;

import java.util.Optional;

@Repository
public class UserRepositoryImpl extends AbstractRepository<AppUser, Long> implements UserRepository {
    private final QAppUser qAppUser = QAppUser.appUser;

    protected UserRepositoryImpl(EntityManager entityManager) {
        super(AppUser.class, entityManager);
    }

    @Override
    public Optional<AppUser> findByEmailOrPhone(String email, String phone) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qAppUser)
                .where(qAppUser.email.eq(email).or(qAppUser.phone.eq(phone)))
                .fetchOne());
    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qAppUser)
                .where(qAppUser.email.eq(email))
                .fetchOne());
    }

    @Override
    public Optional<AppUser> findByPhone(String phone) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qAppUser)
                .where(qAppUser.phone.eq(phone))
                .fetchOne());
    }


    @Override
    public Optional<AppUser> findById(Long id) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qAppUser)
                .where(qAppUser.id.eq(id))
                .fetchOne());
    }
}
