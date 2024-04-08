package vn.edu.hcmuaf.fit.cinemix_api.repository.passwordResetOTP;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.PasswordResetOTP;
import vn.edu.hcmuaf.fit.cinemix_api.entity.QPasswordResetOTP;

import java.util.Optional;

@Repository
public class PasswordResetOTPRepositoryImpl extends AbstractRepository<PasswordResetOTP,Long> implements PasswordResetOTPRepository {
    QPasswordResetOTP qPasswordResetOTP =  QPasswordResetOTP.passwordResetOTP;
    protected PasswordResetOTPRepositoryImpl(EntityManager entityManager) {
        super(PasswordResetOTP.class, entityManager);
    }

    @Override
    public Optional<PasswordResetOTP> findByOTP(String OTP) {
        return Optional.ofNullable(queryFactory.selectFrom(qPasswordResetOTP)
                .where(qPasswordResetOTP.OTP.eq(OTP))
                .fetchOne());

    }

    @Override
    public Optional<PasswordResetOTP> findByUser(Long id) {
        return Optional.ofNullable(queryFactory.selectFrom(qPasswordResetOTP)
                .where(qPasswordResetOTP.user.id.eq(id))
                .fetchOne());
    }

    @Override
    public Optional<PasswordResetOTP> findByEmail(String email) {
        return Optional.ofNullable(queryFactory.selectFrom(qPasswordResetOTP)
                .where(qPasswordResetOTP.user.email.eq(email))
                .fetchOne());
    }
}
