package vn.edu.hcmuaf.fit.cinemix_api.dto.payment.payos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PayOSResponse<T> {
    private String code;
    private String desc;
    private T data;
    private String signature;
}
