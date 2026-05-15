package com.hilberto.teste.domain.model;

import com.hilberto.teste.infrastructure.exception.ApiException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Coupon {
    private final String code;
    private String description;
    private Double discountValue;
    private LocalDateTime expirationDate;
    private Boolean published;
    private Boolean deleted;

    public Coupon(String code, String description, Double discountValue, LocalDateTime expirationDate, Boolean published) {
        this.code = cleanCode(code);
        this.description = description;
        this.discountValue = validateDiscount(discountValue);
        this.expirationDate = validateExpiration(expirationDate);
        this.published = published;
        this.deleted = false;
    }

    public String cleanCode(String code){
        if(Objects.isNull(code)){
            throw new ApiException("Code cannot be null", HttpStatus.CONFLICT, "Code cannot be null");
        }
        String codeFormat = code.replaceAll("[^a-zA-Z0-9]", "");
        if( codeFormat.length() != 6 ){
            throw new ApiException("Code must have 6 characters", HttpStatus.CONFLICT, "Code must have 6 characters");
        }
        return codeFormat;
    }

    private Double validateDiscount(Double discountValue){
        if(discountValue < 0.5) throw new ApiException("Discount value must be greater than 0.5", HttpStatus.CONFLICT, "Discount value must be greater than 0.5");
        return discountValue;
    }

    private LocalDateTime validateExpiration(LocalDateTime date){
        if (Objects.isNull(date) || date.isBefore(LocalDateTime.now())) throw new ApiException("Invalid expiration date", HttpStatus.CONFLICT, "Invalid expiration date");
        return date;
    }
    public void delete() {
        this.deleted = true;
    }
}
