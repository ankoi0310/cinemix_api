package vn.edu.hcmuaf.fit.cinemix_api.dto.movie;

import lombok.Data;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.movie.MovieState;

@Data
public class MovieSearch {
    private String name;
    private MovieState state;
}
