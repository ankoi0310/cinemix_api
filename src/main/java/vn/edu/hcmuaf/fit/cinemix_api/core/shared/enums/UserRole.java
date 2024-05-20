package vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums;

import jakarta.persistence.AttributeConverter;

public enum UserRole {
    ADMIN,
    MEMBER;

    public static class Converter implements AttributeConverter<UserRole, String> {
        @Override
        public String convertToDatabaseColumn(UserRole attribute) {
            return attribute.name();
        }

        @Override
        public UserRole convertToEntityAttribute(String dbData) {
            return UserRole.valueOf(dbData);
        }
    }
}
