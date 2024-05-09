package vn.edu.hcmuaf.fit.cinemix_api.dto.movie;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.movie.MovieState;
import vn.edu.hcmuaf.fit.cinemix_api.dto.genre.GenreDTO;

import java.time.LocalDate;
import java.util.List;

@Data
public class MovieCreate {
    @NotBlank
    private String name;

    @NotEmpty
    private List<GenreDTO> genres;

    @NotBlank
    private String description;

    @NotBlank
    private String directors;

    @NotBlank
    private String actors;

    @Positive(message = "Thời lượng phải lớn hơn 0")
    @NotNull(message = "Thời lượng không được để trống")
    private int duration; // in minutes

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate releasedDate;

    private MultipartFile poster;
    private MultipartFile banner;
    private String trailerUrl;
    private MovieState state;
}
