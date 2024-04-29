package vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.otp;

import jakarta.persistence.AttributeConverter;

public enum OTPType {
    REGISTER,
    FORGOT_PASSWORD;

    public static class Converter implements AttributeConverter<OTPType, String> {
        @Override
        public String convertToDatabaseColumn(OTPType attribute) {
            return attribute.name().toLowerCase();
        }

        @Override
        public OTPType convertToEntityAttribute(String dbData) {
            return OTPType.valueOf(dbData.toUpperCase());
        }
    }
}
