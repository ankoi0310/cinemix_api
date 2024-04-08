package vn.edu.hcmuaf.fit.cinemix_api.service.auth.verifyUser;

import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.verificationUser.VerificationUserRequest;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppUser;
import vn.edu.hcmuaf.fit.cinemix_api.entity.VerificationUser;

public interface VerificationUserService {
    public VerificationUser create(AppUser user);

    public VerificationUser findByRequest(VerificationUserRequest request);

    public void increaseFailedAttempts(String email);

}
