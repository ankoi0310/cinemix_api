package vn.edu.hcmuaf.fit.cinemix_api.core.shared.constants;

import org.springframework.beans.factory.annotation.Value;

public class MailConstant {
    @Value("${mail.address.noreply}")
    public static String NOREPLY_ADDRESS;

    public static final String NOREPLY_NAME = "Cinemix";

    @Value("${mail.address.support}")
    public static String SUPPORT_ADDRESS;

    public static final String SUPPORT_NAME = "Cinemix Support";

    public static final String VERIFICATION_SUBJECT = "Cinemix - Xác thực tài khoản";
}
