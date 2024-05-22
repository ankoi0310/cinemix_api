package vn.edu.hcmuaf.fit.cinemix_api.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.NotFoundException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.user.UserProfileDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.user.UserProfileRequest;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppUser;
import vn.edu.hcmuaf.fit.cinemix_api.mapper.UserMapper;
import vn.edu.hcmuaf.fit.cinemix_api.repository.user.UserRepository;
import vn.edu.hcmuaf.fit.cinemix_api.service.invoice.InvoiceService;
import vn.edu.hcmuaf.fit.cinemix_api.utils.AppUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final InvoiceService invoiceService;

    @Override
    public UserProfileDTO getProfile() throws BaseException {
        try {
            String email = AppUtils.getCurrentUsername();

            AppUser user = userRepository.findByEmail(email)
                                         .orElseThrow(() -> new NotFoundException("Người dùng không tồn tại!"));

            return userMapper.toUserProfileDTO(user);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException("Lỗi khi lấy thông tin người dùng!");
        }
    }

    @Override
    public UserProfileDTO updateProfile(UserProfileRequest request) throws BaseException {
        try {
            String email = AppUtils.getCurrentUsername();

            AppUser user = userRepository.findByEmail(email)
                                         .orElseThrow(() -> new NotFoundException("Người dùng không tồn tại!"));

            if (!user.isPhoneVerified() && !request.getPhone().equals(user.getPhone())) {
                user.setPhone(request.getPhone());
            }

            if (!request.getFullName().equals(user.getUserInfo().getFullName())) {
                user.getUserInfo().setFullName(request.getFullName());
            }

            userRepository.save(user);

            return userMapper.toUserProfileDTO(user);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException("Lỗi khi cập nhật thông tin người dùng!");
        }
    }

    @Override
    public List<InvoiceDTO> getBookingHistory() throws BaseException {
        String email = AppUtils.getCurrentUsername();
        AppUser user = userRepository.findByEmail(email)
                                     .orElseThrow(() -> new NotFoundException("Người dùng không tồn tại!"));

        return invoiceService.getInvoicesByUser(user);
    }
}
