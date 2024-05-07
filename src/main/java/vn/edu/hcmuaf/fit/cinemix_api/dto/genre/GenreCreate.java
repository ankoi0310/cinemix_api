package vn.edu.hcmuaf.fit.cinemix_api.dto.genre;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenreCreate {
    @NotBlank
    private String name;

    private String description;
}
