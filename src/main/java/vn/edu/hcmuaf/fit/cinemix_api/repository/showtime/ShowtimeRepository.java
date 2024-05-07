package vn.edu.hcmuaf.fit.cinemix_api.repository.showtime;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.cinemix_api.dto.showtime.ShowtimeSearch;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Showtime;

import java.time.LocalDate;
import java.util.List;

@NoRepositoryBean
public interface ShowtimeRepository extends CustomRepository<Showtime, Long> {
    List<Showtime> search(ShowtimeSearch search);

    List<Showtime> findByMovieId(Long id);

    List<Showtime> findByDate(LocalDate date);
}
