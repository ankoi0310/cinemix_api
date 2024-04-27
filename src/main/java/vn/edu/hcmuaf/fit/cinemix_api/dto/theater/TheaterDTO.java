package vn.edu.hcmuaf.fit.cinemix_api.dto.theater;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.dto.BaseDTO;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TheaterDTO extends BaseDTO {
    private String name;
    private String address;
    private String hotline;
    private String image;
    private String state;
}
