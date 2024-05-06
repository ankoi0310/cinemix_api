package vn.edu.hcmuaf.fit.cinemix_api.repository.movie;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.cinemix_api.dto.movie.MovieSearch;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Movie;
import vn.edu.hcmuaf.fit.cinemix_api.entity.QMovie;

import java.util.List;
import java.util.Optional;

@Repository
public class MovieRepositoryImpl extends AbstractRepository<Movie, Long> implements MovieRepository {
    private final QMovie qMovie = QMovie.movie;

    protected MovieRepositoryImpl(EntityManager entityManager) {
        super(Movie.class, entityManager);
    }

    @Override
    public Page<Movie> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    @Override
    public List<Movie> search(MovieSearch movieSearch) {
        BooleanBuilder predicate = buildSearchPredicate(movieSearch);
        return queryFactory.selectFrom(qMovie).where(predicate).fetch();
    }

    @Override
    public Optional<Movie> findByName(String name) {
        return Optional.ofNullable(queryFactory.selectFrom(qMovie).where(qMovie.name.equalsIgnoreCase(name))
                                               .fetchFirst());
    }

    private BooleanBuilder buildSearchPredicate(MovieSearch movieSearch) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (movieSearch.getName() != null) {
            predicate.and(qMovie.name.containsIgnoreCase(movieSearch.getName()));
        }

        if (movieSearch.getState() != null) {
            predicate.and(qMovie.state.eq(movieSearch.getState()));
        }

        return predicate;
    }
}
