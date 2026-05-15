package com.hilberto.teste.infrastructure.persistence.mapper;

import com.hilberto.teste.domain.dto.request.CouponRequest;
import com.hilberto.teste.domain.dto.response.CouponResponse;
import com.hilberto.teste.domain.model.Coupon;
import com.hilberto.teste.infrastructure.persistence.entity.CouponEntity;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {
    public CouponEntity convertToEntity(Coupon coupon) {
        if (coupon == null) return null;
        return CouponEntity.builder()
                .code(coupon.getCode())
                .description(coupon.getDescription())
                .discountValue(coupon.getDiscountValue())
                .expirationDate(coupon.getExpirationDate())
                .published(coupon.getPublished())
                .build();
    }

    public CouponResponse convertToResponse(CouponEntity coupon) {
        if (coupon == null) return null;
        return new CouponResponse(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDescription(),
                coupon.getDiscountValue(),
                coupon.getExpirationDate(),
                coupon.getPublished());
    }
}
