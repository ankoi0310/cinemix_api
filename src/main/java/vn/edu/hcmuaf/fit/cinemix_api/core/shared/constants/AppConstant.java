package vn.edu.hcmuaf.fit.cinemix_api.core.shared.constants;


import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.UserRole;

public class AppConstant {
    public static final UserRole DEFAULT_ROLE = UserRole.MEMBER;

    public static final long OTP_EXPIRED_TIME = 5; // 5 minutes

    public static final int OTP_CODE_LENGTH = 6;
}
