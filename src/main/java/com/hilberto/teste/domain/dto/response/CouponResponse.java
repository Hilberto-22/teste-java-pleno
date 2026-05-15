package com.hilberto.teste.domain.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CouponResponse {
    private UUID id;
    private String code;
    private String description;
    private Double discountValue;
    private LocalDateTime expirationDate;
    private Boolean published;

    public CouponResponse(UUID id, String code, String description, Double discountValue, LocalDateTime expirationDate, Boolean published) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.discountValue = discountValue;
        this.expirationDate = expirationDate;
        this.published = published;
    }
}
