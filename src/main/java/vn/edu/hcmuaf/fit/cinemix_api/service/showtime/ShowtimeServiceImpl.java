package vn.edu.hcmuaf.fit.cinemix_api.service.showtime;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.*;
import vn.edu.hcmuaf.fit.cinemix_api.dto.showtime.ShowtimeDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.showtime.ShowtimeSearch;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Showtime;
import vn.edu.hcmuaf.fit.cinemix_api.mapper.ShowtimeMapper;
import vn.edu.hcmuaf.fit.cinemix_api.repository.showtime.ShowtimeRepository;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ShowtimeServiceImpl implements ShowtimeService {
    private final ShowtimeRepository showtimeRepository;
    private final ShowtimeMapper showtimeMapper;

    @Override
    public List<ShowtimeDTO> getAllShowtimes() {
        List<Showtime> showtimes = showtimeRepository.findAll();
        return showtimeMapper.toShowtimeDTOs(showtimes);
    }

    @Override
    public List<ShowtimeDTO> search(ShowtimeSearch showtimeSearch) {
        List<Showtime> showtimes = showtimeRepository.search(showtimeSearch);
        return showtimeMapper.toShowtimeDTOs(showtimes);
    }

    @Override
    public List<ShowtimeDTO> getShowtimesByMovieId(Long id) throws BaseException {
        try {
            List<Showtime> showtimes = showtimeRepository.findByMovieId(id);
            return showtimeMapper.toShowtimeDTOs(showtimes);
        } catch (Exception e) {
            log.error("Error getting showtimes by movie id: {}", e.getMessage());
            throw new ServiceUnavailableException("Không thể lấy thông tin lịch chiếu");
        }
    }

    @Override
    public List<ShowtimeDTO> getShowtimesByDate(LocalDate date) {
        List<Showtime> showtimes = showtimeRepository.findByDate(date);
        return showtimeMapper.toShowtimeDTOs(showtimes);
    }

    @Override
    public ShowtimeDTO getShowtimeById(Long id) throws BaseException {
        try {
            Showtime showtime = showtimeRepository
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy lịch chiếu với id: " + id));
            return showtimeMapper.toShowtimeDTO(showtime);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error getting showtime by id: {}", e.getMessage());
            throw new ServiceUnavailableException("Không thể lấy thông tin lịch chiếu");
        }
    }

    @Override
    public ShowtimeDTO createShowtime(ShowtimeDTO showtimeDTO) throws BaseException {
        return null;
    }

    @Override
    public ShowtimeDTO updateShowtime(ShowtimeDTO showtimeDTO) throws BaseException {
        return null;
    }

    @Override
    public void deleteShowtime(Long id) throws BaseException {
        try {
            Showtime showtime = showtimeRepository.findById(id)
                                                  .orElseThrow(() -> new NotFoundException("Showtime not found"));
            showtimeRepository.delete(showtime);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error deleting showtime: {}", e.getMessage());
        }
    }
}
