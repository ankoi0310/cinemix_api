package vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums;

import jakarta.persistence.AttributeConverter;

public enum UserRole {
    ADMIN,
    MANAGER,
    EMPLOYEE,
    MEMBER;

    public static UserRole getRole(String role) {
        return switch (role) {
            case "ADMIN" -> ADMIN;
            case "MANAGER" -> MANAGER;
            case "EMPLOYEE" -> EMPLOYEE;
            case "MEMBER" -> MEMBER;
            default -> null;
        };
    }

    public static class Converter implements AttributeConverter<UserRole, String> {
        @Override
        public String convertToDatabaseColumn(UserRole attribute) {
            return attribute.name();
        }

        @Override
        public UserRole convertToEntityAttribute(String dbData) {
            return UserRole.getRole(dbData);
        }
    }
}
