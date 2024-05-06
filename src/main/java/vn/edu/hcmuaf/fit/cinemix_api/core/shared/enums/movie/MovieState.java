package vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.movie;

import jakarta.persistence.AttributeConverter;

public enum MovieState {
    SHOWING,
    COMING_SOON,
    END;

    public static class Converter implements AttributeConverter<MovieState, String> {
        @Override
        public String convertToDatabaseColumn(MovieState attribute) {
            return attribute.name().toLowerCase();
        }

        @Override
        public MovieState convertToEntityAttribute(String dbData) {
            return MovieState.valueOf(dbData.toUpperCase());
        }
    }
}
