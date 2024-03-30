package vn.edu.hcmuaf.fit.cinemix_api.repository.genre;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.cinemix_api.dto.genre.GenreSearch;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Genre;

import java.util.List;
import java.util.Optional;


@NoRepositoryBean
public interface GenreRepository extends CustomRepository<Genre, Long> {
    List<Genre> search(GenreSearch genreSearch);
    Optional<Genre> findByName(String name);
}
