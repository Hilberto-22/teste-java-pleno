package com.hilberto.teste.domain.dto.request;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CouponRequest {
    private String code;
    private String description;
    private Double discountValue;
    private LocalDateTime expirationDate;
    private boolean published;
 }
