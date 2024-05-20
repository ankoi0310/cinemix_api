package vn.edu.hcmuaf.fit.cinemix_api.service.user;

import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.user.UserProfileDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.user.UserProfileRequest;

public interface UserService {
    UserProfileDTO getProfile() throws BaseException;

    UserProfileDTO updateProfile(UserProfileRequest request) throws BaseException;
}
