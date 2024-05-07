package vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums;

import jakarta.persistence.AttributeConverter;
import lombok.Getter;

@Getter
public enum TheaterState {
    ACTIVE,
    CLOSED,
    UNDER_MAINTENANCE;

    public static class Converter implements AttributeConverter<TheaterState, String> {
        @Override
        public String convertToDatabaseColumn(TheaterState theaterState) {
            return theaterState.name().toLowerCase();
        }

        @Override
        public TheaterState convertToEntityAttribute(String s) {
            return TheaterState.valueOf(s.toUpperCase());
        }
    }
}
