package vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.movie;

import jakarta.persistence.AttributeConverter;
import lombok.Getter;

@Getter
public enum MovieFormat {
    TWO_D("2D"),
    THREE_D("3D"),
    FOUR_D("4D"),
    IMAX("IMAX"),
    DOLBY_ATMOS("Dolby Atmos"),
    SCREENX("ScreenX"),
    MX4D("MX4D");

    private final String value;

    MovieFormat(String value) {
        this.value = value;
    }

    public static MovieFormat fromValue(String value) {
        for (MovieFormat format : MovieFormat.values()) {
            if (format.getValue().equals(value)) {
                return format;
            }
        }
        return null;
    }

    public static class Converter implements AttributeConverter<MovieFormat, String> {
        @Override
        public String convertToDatabaseColumn(MovieFormat attribute) {
            return attribute.getValue();
        }

        @Override
        public MovieFormat convertToEntityAttribute(String dbData) {
            return fromValue(dbData);
        }
    }
}
