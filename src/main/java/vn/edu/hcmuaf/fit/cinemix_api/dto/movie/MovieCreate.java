package vn.edu.hcmuaf.fit.cinemix_api.dto.movie;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.fit.cinemix_api.dto.genre.GenreDTO;

import java.util.Date;
import java.util.List;

@Data
public class MovieCreate {
    @NotBlank
    private String title;

    @NotEmpty
    private List<GenreDTO> genres;

    @NotBlank
    private String synopsis;

    @NotBlank
    private String description;

    @NotBlank
    private String director;

    private List<String> actors;

    @Positive(message = "Thời lượng phải lớn hơn 0")
    @NotNull(message = "Thời lượng không được để trống")
    private Integer duration; // in minutes

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date releaseDate;

    private MultipartFile poster;
    private String trailerUrl;
}
