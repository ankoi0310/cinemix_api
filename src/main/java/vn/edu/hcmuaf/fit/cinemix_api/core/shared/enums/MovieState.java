package vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums;

import lombok.Getter;

@Getter
public enum MovieState {
    SHOWING("Đang chiếu"),
    COMING_SOON("Sắp chiếu"),
    END("Ngừng chiếu");

    private final String value;

    MovieState(String value) {
        this.value = value;
    }

    public static MovieState fromValue(String value) {
        for (MovieState state : MovieState.values()) {
            if (state.value.equals(value)) {
                return state;
            }
        }
        return null;
    }
}
