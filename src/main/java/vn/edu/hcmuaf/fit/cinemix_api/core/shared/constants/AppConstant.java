package vn.edu.hcmuaf.fit.cinemix_api.core.shared.constants;


import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.UserRole;

public class AppConstant {
    public static final UserRole DEFAULT_ROLE = UserRole.CUSTOMER;

    public static final long VERIFICATION_OTP_EXPIRED_DATE = 1; // 1 day

    public static final long PASSWORD_RESET_OTP_EXPIRED_DATE = 30; // 30 minutes
    public static final long JWT_REFRESH_TOKEN_EXPIRED_DATE = 3; // 7 day

    public static final int OTP_LENGTH = 6;

    public static final int MAX_FAILED_ATTEMPT = 3;

    public static final int USER_LOCK_TIME = 15; //15 minutes


}
