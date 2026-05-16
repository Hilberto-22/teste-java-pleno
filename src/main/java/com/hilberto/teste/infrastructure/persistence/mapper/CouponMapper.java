package com.hilberto.teste.infrastructure.persistence.mapper;
import com.hilberto.teste.domain.dto.response.CouponResponse;
import com.hilberto.teste.domain.enums.CouponStatus;
import com.hilberto.teste.domain.model.Coupon;
import com.hilberto.teste.infrastructure.persistence.entity.CouponEntity;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {
    public CouponEntity convertToEntity(Coupon coupon) {
        if (coupon == null) return null;
        CouponEntity entity = new CouponEntity();
        entity.setCode(coupon.getCode());
        entity.setDescription(coupon.getDescription());
        entity.setDiscountValue(coupon.getDiscountValue());
        entity.setExpirationDate(coupon.getExpirationDate());
        entity.setPublished(coupon.isPublished());
        entity.setStatus(CouponStatus.ACTIVE);

        return entity;
    }

    public CouponResponse convertToResponse(CouponEntity coupon) {
        if (coupon == null) return null;
        return new CouponResponse(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDescription(),
                coupon.getDiscountValue(),
                coupon.getExpirationDate(),
                coupon.isPublished(),
                coupon.isRedeemed(),
                coupon.getStatus());
    }
}
