package vn.edu.hcmuaf.fit.cinemix_api.repository.invoice;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.*;

import java.util.List;
import java.util.Optional;

@Repository
public class InvoiceRepositoryImpl extends AbstractRepository<Invoice, Long> implements InvoiceRepository {
    private final QInvoice qInvoice = QInvoice.invoice;

    protected InvoiceRepositoryImpl(EntityManager entityManager) {
        super(Invoice.class, entityManager);
    }

    @Override
    public List<Invoice> findByUser(AppUser user) {
        return queryFactory.selectFrom(qInvoice)
                           .where(qInvoice.user.eq(user).and(qInvoice.paid.eq(true)))
                           .fetch();
    }

    @Override
    public Optional<Invoice> findByCode(int code) {
        return Optional.ofNullable(queryFactory.selectFrom(qInvoice)
                                               .where(qInvoice.code.eq(code))
                                               .fetchOne());
    }
}
