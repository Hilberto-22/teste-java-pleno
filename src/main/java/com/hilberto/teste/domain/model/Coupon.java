package com.hilberto.teste.domain.model;

import com.hilberto.teste.infrastructure.exception.ApiException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Coupon {
    private String code;
    private String description;
    private Double discountValue;
    private LocalDateTime expirationDate;
    private boolean published;
    private boolean deleted;
    private boolean redeemed;

    public Coupon(String code, String description, Double discountValue, LocalDateTime expirationDate , boolean published) {
        this.code = cleanCode(code);
        this.description = validateDescription(description);
        this.discountValue = validateDiscount(discountValue);
        this.expirationDate = validateExpiration(expirationDate);
        this.published = validatePublished(published);
        this.deleted = false;
        this.redeemed = false;
    }

    private boolean validatePublished(boolean published){
        if (published) {
            return true;
        }
        return false;
    }

    public String validateDescription(String description){
        if(Objects.isNull(description)){
            throw new ApiException("The description is required", HttpStatus.BAD_REQUEST, "The description is required");
        }
        return description;
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
        if(Objects.isNull(discountValue) || discountValue < 0.5) {
            throw new ApiException("Discount value must be greater than 0.5", HttpStatus.CONFLICT, "Discount value must be greater than 0.5");
        }
        return discountValue;
    }

    private LocalDateTime validateExpiration(LocalDateTime date){
        if (Objects.isNull(date) || date.isBefore(LocalDateTime.now())) throw new ApiException("Invalid expiration date", HttpStatus.CONFLICT, "Expiration date must be in the future or cannot be null");
        return date;
    }
    public void delete() {
        this.deleted = true;
    }
}
