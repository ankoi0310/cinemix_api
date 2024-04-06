package vn.edu.hcmuaf.fit.cinemix_api.repository.passwordResetToken;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.PasswordResetToken;
import vn.edu.hcmuaf.fit.cinemix_api.entity.QPasswordResetToken;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PasswordResetTokenRepositoryImpl extends AbstractRepository<PasswordResetToken,Long> implements PasswordResetTokenRepository {
    QPasswordResetToken qPasswordResetToken =  QPasswordResetToken.passwordResetToken;
    protected PasswordResetTokenRepositoryImpl(EntityManager entityManager) {
        super(PasswordResetToken.class, entityManager);
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return Optional.ofNullable(queryFactory.selectFrom(qPasswordResetToken)
                .where(qPasswordResetToken.token.eq(token))
                .fetchOne());

    }
}
