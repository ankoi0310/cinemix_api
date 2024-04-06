package vn.edu.hcmuaf.fit.cinemix_api.repository.jwtRefreshToken;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.JWTRefreshToken;
import vn.edu.hcmuaf.fit.cinemix_api.entity.QJWTRefreshToken;

import java.util.Optional;

@Repository
public class JWTRefreshTokenRepositoryImpl extends AbstractRepository<JWTRefreshToken,Long> implements JWTRefreshTokenRepository {

    QJWTRefreshToken qJWTRefreshToken = QJWTRefreshToken.jWTRefreshToken;
    protected JWTRefreshTokenRepositoryImpl( EntityManager entityManager) {
        super(JWTRefreshToken.class, entityManager);
    }

    @Override
    public Optional<JWTRefreshToken> findByToken(String token) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qJWTRefreshToken)
                .where(qJWTRefreshToken.token.eq(token))
                .fetchOne());
    }

    @Override
    public Optional<JWTRefreshToken> findByUser(Long userId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qJWTRefreshToken)
                .where(qJWTRefreshToken.user.id.eq(userId))
                .fetchOne());
    }
}
