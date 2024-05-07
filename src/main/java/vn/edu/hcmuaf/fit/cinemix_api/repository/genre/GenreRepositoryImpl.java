package vn.edu.hcmuaf.fit.cinemix_api.repository.genre;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.cinemix_api.dto.genre.GenreSearch;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Genre;
import vn.edu.hcmuaf.fit.cinemix_api.entity.QGenre;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryImpl extends AbstractRepository<Genre, Long> implements GenreRepository {
    private final QGenre qGenre = QGenre.genre;

    protected GenreRepositoryImpl(EntityManager entityManager) {
        super(Genre.class, entityManager);
    }

    @Override
    public List<Genre> search(GenreSearch genreSearch) {
        return queryFactory.selectFrom(qGenre)
                .where(buildSearchPredicate(genreSearch))
                .fetch();
    }

    @Override
    public Optional<Genre> findByName(String name) {
        return Optional.ofNullable(queryFactory.selectFrom(qGenre)
                .where(qGenre.name.equalsIgnoreCase(name))
                .fetchFirst());
    }

    private BooleanBuilder buildSearchPredicate(GenreSearch genreSearch) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (genreSearch.getName() != null) {
            predicate.and(qGenre.name.containsIgnoreCase(genreSearch.getName()));
        }

        
        return predicate;
    }
}
