package vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums;

import jakarta.persistence.AttributeConverter;
import lombok.Getter;

@Getter
public enum TheaterState {
    ACTIVE("active", "Active"),
    CLOSED("closed", "Closed"),
    UNDER_MAINTENANCE("under_maintenance", "Under Maintenance");

    private final String value;
    private final String description;

    TheaterState(String value, String description) {
        this.value = value;
        this.description = description;
    }


    public static TheaterState fromValue(String value) {
        for (TheaterState state : TheaterState.values()) {
            if (state.getValue().equals(value)) {
                return state;
            }
        }
        return null;
    }

    public static class TheaterStateConverter implements AttributeConverter<TheaterState, String> {
        @Override
        public String convertToDatabaseColumn(TheaterState theaterState) {
            return theaterState.getValue();
        }

        @Override
        public TheaterState convertToEntityAttribute(String s) {
            return TheaterState.fromValue(s);
        }
    }
}
