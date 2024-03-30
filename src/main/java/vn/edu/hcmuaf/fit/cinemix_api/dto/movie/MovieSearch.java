package vn.edu.hcmuaf.fit.cinemix_api.dto.movie;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import vn.edu.hcmuaf.fit.cinemix_api.dto.genre.GenreDTO;

import java.util.Date;
import java.util.List;

@Data
public class MovieSearch {
    private String title;
    private List<GenreDTO> genres;
    private String director;
    private List<String> actors;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date releaseDate;
}
