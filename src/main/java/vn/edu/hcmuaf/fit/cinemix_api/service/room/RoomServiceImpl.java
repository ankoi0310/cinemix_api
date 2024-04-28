package vn.edu.hcmuaf.fit.cinemix_api.service.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.NotFoundException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.room.RoomDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Room;
import vn.edu.hcmuaf.fit.cinemix_api.mapper.RoomMapper;
import vn.edu.hcmuaf.fit.cinemix_api.repository.room.RoomRepository;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public List<RoomDTO> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return roomMapper.toRoomDTOs(rooms);
    }

    @Override
    public RoomDTO getRoomById(Long id) throws BaseException {
        try {
            Room room = roomRepository.findById(id).orElseThrow(() -> new NotFoundException("Room not found"));
            return roomMapper.toRoomDTO(room);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error getting room by id: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public RoomDTO createRoom(RoomDTO roomDTO) throws BaseException {
        return null;
    }

    @Override
    public RoomDTO updateRoom(RoomDTO roomDTO) throws BaseException {
        return null;
    }

    @Override
    public void deleteRoom(Long id) {

    }
}
