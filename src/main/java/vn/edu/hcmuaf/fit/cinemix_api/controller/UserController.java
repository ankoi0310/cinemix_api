package vn.edu.hcmuaf.fit.cinemix_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.user.UserProfileDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.user.UserProfileRequest;
import vn.edu.hcmuaf.fit.cinemix_api.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<HttpResponse> getProfile() throws BaseException {
        UserProfileDTO userProfileDTO = userService.getProfile();
        return ResponseEntity.ok(HttpResponse.success(userProfileDTO, "Lấy thông tin người dùng thành công!"));
    }

    @PutMapping("/profile")
    public ResponseEntity<HttpResponse> updateProfile(@RequestBody UserProfileRequest request) throws BaseException {
        UserProfileDTO userProfileDTO = userService.updateProfile(request);
        return ResponseEntity.ok(HttpResponse.success(userProfileDTO, "Cập nhật thông tin người dùng thành công!"));
    }

    @GetMapping("/booking-history")
    public ResponseEntity<HttpResponse> getBookingHistory() throws BaseException {
        List<InvoiceDTO> invoices = userService.getBookingHistory();
        return ResponseEntity.ok(HttpResponse.success(invoices, "Lấy lịch sử đặt vé thành công!"));
    }
}
