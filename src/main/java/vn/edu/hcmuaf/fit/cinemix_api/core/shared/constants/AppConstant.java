package vn.edu.hcmuaf.fit.cinemix_api.core.shared.constants;


import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.UserRole;

public class AppConstant {
    public static final UserRole DEFAULT_ROLE = UserRole.CUSTOMER;

    public static final long VERIFICATION_TOKEN_EXPIRED_DATE = 1; // 1 day

    public static final long PASSWORD_RESET_TOKEN_EXPIRED_DATE = 30; // 30 minutes
    public static final long JWT_REFRESH_TOKEN_EXPIRED_DATE = 3; // 7 day


}
