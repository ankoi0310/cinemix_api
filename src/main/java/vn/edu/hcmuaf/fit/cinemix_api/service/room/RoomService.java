package vn.edu.hcmuaf.fit.cinemix_api.service.room;

import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.room.RoomDTO;

import java.util.List;

public interface RoomService {
    List<RoomDTO> getAllRooms();

    RoomDTO getRoomById(Long id) throws BaseException;

    RoomDTO createRoom(RoomDTO roomDTO) throws BaseException;

    RoomDTO updateRoom(RoomDTO roomDTO) throws BaseException;

    void deleteRoom(Long id) throws BaseException;
}
