package com.hilberto.teste.domain.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CouponRequest {

    @NotBlank(message = "Code cannot be null")
    @Size(min = 6, max = 6, message = "Code must have 6 characters")
    private String code;

    @NotBlank(message = "Description cannot be null")
    private String description;

    @NotBlank(message = "Discount value cannot be null")
    @Positive(message = "Discount value must be greater than 0.5")
    private Double discountValue;

    @NotBlank(message = "Expiration date cannot be null")
    @Future(message = "Expiration date must be in the future")
    private LocalDateTime expirationDate;
 }
