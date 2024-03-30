package vn.edu.hcmuaf.fit.cinemix_api.dto.movie;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.dto.BaseDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.genre.GenreDTO;

import java.util.Date;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"id", "title", "genres", "synopsis", "description", "director", "actors", "duration",
        "releaseDate", "posterUrl", "trailerUrl", "state", "createdDate", "modifiedDate"})
public class MovieDTO extends BaseDTO {
    private String title;
    private List<GenreDTO> genres;
    private String synopsis;
    private String description;
    private String director;
    private List<String> actors;
    private Integer duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date releaseDate;
    private String posterUrl;
    private String trailerUrl;

    @Enumerated(EnumType.STRING)
    private String state;
}
