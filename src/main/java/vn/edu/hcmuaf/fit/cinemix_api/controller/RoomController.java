package vn.edu.hcmuaf.fit.cinemix_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.room.RoomDTO;
import vn.edu.hcmuaf.fit.cinemix_api.service.room.RoomService;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<HttpResponse> getAllRooms() {
        List<RoomDTO> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(HttpResponse.success(rooms, "Lấy danh sách phòng chiếu thành công"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getRoomById(@PathVariable Long id) throws BaseException {
        RoomDTO room = roomService.getRoomById(id);
        return ResponseEntity.ok(HttpResponse.success(room, "Lấy thông tin phòng chiếu thành công"));
    }
}
