package vn.edu.hcmuaf.fit.cinemix_api.repository.verificationUser;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.QVerificationUser;
import vn.edu.hcmuaf.fit.cinemix_api.entity.VerificationUser;

import java.util.Optional;

@Repository
public class VerificationUserRepositoryImpl extends AbstractRepository<VerificationUser, Long> implements VerificationUserRepository {
    private final QVerificationUser qVerificationUser = QVerificationUser.verificationUser;

    protected VerificationUserRepositoryImpl(EntityManager entityManager) {
        super(VerificationUser.class, entityManager);
    }

    @Override
    public Optional<VerificationUser> findByOTP(String token) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qVerificationUser)
                .where(qVerificationUser.OTP.eq(token))
                .fetchOne());
    }

    @Override
    public Optional<VerificationUser> findByUser(Long id) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qVerificationUser)
                .where(qVerificationUser.user.id.eq(id))
                .fetchOne());
    }

    @Override
    public Optional<VerificationUser> findByEmail(String email) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qVerificationUser)
                .where(qVerificationUser.user.email.eq(email))
                .fetchOne());
    }
}
