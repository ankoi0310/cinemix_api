package vn.edu.hcmuaf.fit.cinemix_api.repository.movie;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.cinemix_api.dto.genre.GenreDTO;
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
    public List<Movie> search(MovieSearch movieSearch) {
        BooleanBuilder predicate = buildSearchPredicate(movieSearch);
        return queryFactory.selectFrom(qMovie).where(predicate).fetch();
    }

    @Override
    public Optional<Movie> findByTitle(String title) {
        return Optional.ofNullable(queryFactory.selectFrom(qMovie).where(qMovie.title.equalsIgnoreCase(title))
                                               .fetchFirst());
    }

    private BooleanBuilder buildSearchPredicate(MovieSearch movieSearch) {
        BooleanBuilder predicate = new BooleanBuilder();
        if (movieSearch.getTitle() != null) {
            predicate.and(qMovie.title.containsIgnoreCase(movieSearch.getTitle()));
        }

        if (movieSearch.getGenres() != null && !movieSearch.getGenres().isEmpty()) {
            List<Long> genreIds = movieSearch.getGenres().stream().map(GenreDTO::getId).toList();
            predicate.and(qMovie.genres.any().id.in(genreIds));
        }

        if (movieSearch.getDirector() != null) {
            predicate.and(qMovie.director.containsIgnoreCase(movieSearch.getDirector()));
        }

        if (movieSearch.getActors() != null && !movieSearch.getActors().isEmpty()) {
            BooleanBuilder actorPredicate = new BooleanBuilder();
            for (String actor : movieSearch.getActors()) {
                actorPredicate.or(qMovie.actors.any().containsIgnoreCase(actor));
            }
            predicate.and(actorPredicate);
        }

        if (movieSearch.getReleaseDate() != null) {
            predicate.and(qMovie.releaseDate.eq(movieSearch.getReleaseDate()));
        }
        return predicate;
    }
}
