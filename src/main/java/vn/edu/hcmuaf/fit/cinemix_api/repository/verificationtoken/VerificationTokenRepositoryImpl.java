package vn.edu.hcmuaf.fit.cinemix_api.repository.verificationtoken;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.QVerificationToken;
import vn.edu.hcmuaf.fit.cinemix_api.entity.VerificationToken;

import java.util.Optional;

@Repository
public class VerificationTokenRepositoryImpl extends AbstractRepository<VerificationToken, Long> implements VerificationTokenRepository {
    private final QVerificationToken qVerificationToken = QVerificationToken.verificationToken;

    protected VerificationTokenRepositoryImpl(EntityManager entityManager) {
        super(VerificationToken.class, entityManager);
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qVerificationToken)
                .where(qVerificationToken.token.eq(token))
                .fetchOne());
    }

    @Override
    public Optional<VerificationToken> findByUser(Long id) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qVerificationToken)
                .where(qVerificationToken.user.id.eq(id))
                .fetchOne());
    }
}
