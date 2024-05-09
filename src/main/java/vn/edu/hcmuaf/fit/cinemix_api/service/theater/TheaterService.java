package vn.edu.hcmuaf.fit.cinemix_api.service.theater;

import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.theater.TheaterDTO;

import java.util.List;

public interface TheaterService {
    List<TheaterDTO> getAllTheaters();

    TheaterDTO getTheaterById(Long id) throws BaseException;
}
