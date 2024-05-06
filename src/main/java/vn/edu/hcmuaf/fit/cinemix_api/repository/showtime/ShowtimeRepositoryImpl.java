package vn.edu.hcmuaf.fit.cinemix_api.repository.showtime;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.cinemix_api.dto.showtime.ShowtimeSearch;
import vn.edu.hcmuaf.fit.cinemix_api.entity.QShowtime;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Showtime;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ShowtimeRepositoryImpl extends AbstractRepository<Showtime, Long> implements ShowtimeRepository {
    private final QShowtime qShowtime = QShowtime.showtime;

    protected ShowtimeRepositoryImpl(EntityManager entityManager) {
        super(Showtime.class, entityManager);
    }

    @Override
    public List<Showtime> search(ShowtimeSearch search) {
        return queryFactory.selectFrom(qShowtime)
                           .where(getPredicate(search))
                           .fetch();
    }

    @Override
    public List<Showtime> findByMovieId(Long id) {
        return queryFactory.selectFrom(qShowtime)
                           .where(qShowtime.movie.id.eq(id))
                           .fetch();
    }

    @Override
    public List<Showtime> findByDate(LocalDate date) {
        return queryFactory.selectFrom(qShowtime)
                           .where(qShowtime.date.eq(date))
                           .fetch();
    }

    private BooleanBuilder getPredicate(ShowtimeSearch search) {
        BooleanBuilder predicate = new BooleanBuilder();
        if (search.getMovieId() != null) {
            predicate.and(qShowtime.movie.id.eq(search.getMovieId()));
        }
        if (search.getTheaterId() != null) {
            predicate.and(qShowtime.room.theater.id.eq(search.getTheaterId()));
        }
        if (search.getDate() != null) {
            predicate.and(qShowtime.date.eq(search.getDate()));
        }
        return predicate;
    }
}
