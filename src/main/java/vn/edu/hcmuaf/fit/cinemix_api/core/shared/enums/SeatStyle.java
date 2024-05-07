package vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums;

import jakarta.persistence.AttributeConverter;

public enum SeatStyle {
    SINGLE, COUPLE, BED;

    public static class Converter implements AttributeConverter<SeatStyle, String> {
        @Override
        public String convertToDatabaseColumn(SeatStyle attribute) {
            return attribute.name().toLowerCase();
        }

        @Override
        public SeatStyle convertToEntityAttribute(String dbData) {
            if (dbData == null) {
                return null;
            }
            return SeatStyle.valueOf(dbData.toUpperCase());
        }
    }
}
