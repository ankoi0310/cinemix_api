package vn.edu.hcmuaf.fit.cinemix_api.dto.movie;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MovieUpdate extends MovieCreate {
    private Long id;
}
