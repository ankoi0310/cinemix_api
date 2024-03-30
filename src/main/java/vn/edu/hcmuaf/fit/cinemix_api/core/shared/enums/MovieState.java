package vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums;

import lombok.Getter;

@Getter
public enum MovieState {
    SHOWING("Đang chiếu"),
    COMING_SOON("Sắp chiếu"),
    END("Kết thúc");

    private final String value;

    MovieState(String value) {
        this.value = value;
    }
}
