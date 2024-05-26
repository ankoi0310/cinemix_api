package vn.edu.hcmuaf.fit.cinemix_api.repository.ticket;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.*;

import java.util.Optional;

@NoRepositoryBean
public interface TicketRepository extends CustomRepository<Ticket, Long> {
    Optional<Ticket> findByShowtimeAndSeat(Showtime showtime, Seat seat);
}
