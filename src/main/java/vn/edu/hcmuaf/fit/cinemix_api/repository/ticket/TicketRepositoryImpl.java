package vn.edu.hcmuaf.fit.cinemix_api.repository.ticket;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.*;

import java.util.Optional;

@Repository
public class TicketRepositoryImpl extends AbstractRepository<Ticket, Long> implements TicketRepository {
    private final QTicket qTicket = QTicket.ticket;

    protected TicketRepositoryImpl(EntityManager entityManager) {
        super(Ticket.class, entityManager);
    }

    @Override
    public Optional<Ticket> findByShowtimeAndSeat(Showtime showtime, Seat seat) {
        return Optional.ofNullable(queryFactory.selectFrom(qTicket)
                                               .where(qTicket.showtime.eq(showtime).and(qTicket.seat.eq(seat)))
                                               .fetchOne());
    }
}
