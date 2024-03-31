package vn.edu.hcmuaf.fit.cinemix_api.repository.movie;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.cinemix_api.dto.movie.MovieSearch;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Movie;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface MovieRepository extends CustomRepository<Movie, Long> {
    List<Movie> search(MovieSearch movieSearch);

    Optional<Movie> findByTitle(String title);
}
