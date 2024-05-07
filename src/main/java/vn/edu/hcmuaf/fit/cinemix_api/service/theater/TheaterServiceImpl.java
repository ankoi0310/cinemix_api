package vn.edu.hcmuaf.fit.cinemix_api.service.theater;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.theater.TheaterDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Theater;
import vn.edu.hcmuaf.fit.cinemix_api.mapper.TheaterMapper;
import vn.edu.hcmuaf.fit.cinemix_api.repository.theater.TheaterRepository;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class TheaterServiceImpl implements TheaterService {
    private final TheaterRepository theaterRepository;
    private final TheaterMapper theaterMapper;

    @Override
    public List<TheaterDTO> getAllTheaters() {
        List<Theater> theaters = theaterRepository.findAll();
        return theaterMapper.toTheaterDTOs(theaters);
    }

    @Override
    public TheaterDTO getTheaterById(Long id) throws BaseException {
        Theater theater = theaterRepository.findById(id).orElseThrow(() -> new BaseException("Theater not found"));
        return theaterMapper.toTheaterDTO(theater);
    }
}
