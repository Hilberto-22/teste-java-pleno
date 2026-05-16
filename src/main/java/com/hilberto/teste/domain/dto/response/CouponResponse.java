package com.hilberto.teste.domain.dto.response;

import com.hilberto.teste.domain.CouponStatus;
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
    private CouponStatus status;
    private boolean published;
    private boolean redeemed;

    public CouponResponse(UUID id, String code, String description, Double discountValue, LocalDateTime expirationDate, boolean published, boolean redeemed, CouponStatus status) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.discountValue = discountValue;
        this.expirationDate = expirationDate;
        this.status = status;
        this.published = published;
        this.redeemed = redeemed;
    }
}
