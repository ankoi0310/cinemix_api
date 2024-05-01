package vn.edu.hcmuaf.fit.cinemix_api.dto.movie;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.dto.BaseDTO;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.movie.MovieRating;
import vn.edu.hcmuaf.fit.cinemix_api.dto.genre.GenreDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.showtime.ShowtimeDTO;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"id", "name", "description", "directors", "actors", "genres",
        "releasedDate", "duration", "posterUrl", "trailerUrl", "state", "createdDate", "modifiedDate"})
public class MovieDTO extends BaseDTO {
    private String name;
    private String description;
    private String directors;
    private String actors;
    private List<GenreDTO> genres;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private LocalDate releasedDate;

    private int duration;
    private String country;
    private String language;
    private String localizations;

    @Enumerated(EnumType.STRING)
    private MovieRating rating;

    private String posterUrl;
    private String trailerUrl;
    private String state;

    @JsonIgnore
    @JsonIgnoreProperties("movie")
    private List<ShowtimeDTO> showtimes;
}
