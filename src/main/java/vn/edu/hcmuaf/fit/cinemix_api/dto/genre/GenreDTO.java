package vn.edu.hcmuaf.fit.cinemix_api.dto.genre;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.dto.BaseDTO;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"id", "name", "description"})
public class GenreDTO extends BaseDTO {
    private String name;
    private String description;
}
