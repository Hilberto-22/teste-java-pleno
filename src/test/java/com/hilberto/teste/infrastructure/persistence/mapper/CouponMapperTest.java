package com.hilberto.teste.infrastructure.persistence.mapper;

import com.hilberto.teste.domain.CouponStatus;
import com.hilberto.teste.domain.dto.response.CouponResponse;
import com.hilberto.teste.domain.model.Coupon;
import com.hilberto.teste.infrastructure.persistence.entity.CouponEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CouponMapperTest {

    private final CouponMapper mapper = new CouponMapper();

    @Test
    void shouldReturnNullEntityWhenCouponIsNull() {
        assertNull(mapper.convertToEntity(null));
    }

    @Test
    void shouldConvertCouponToEntity() {
        Coupon coupon = new Coupon(
                "ABC123",
                "Cupom de desconto",
                10.0,
                LocalDateTime.now().plusDays(1),
                false);

        CouponEntity entity = mapper.convertToEntity(coupon);

        assertEquals("ABC123", entity.getCode());
        assertEquals("Cupom de desconto", entity.getDescription());
        assertEquals(10.0, entity.getDiscountValue());
        assertEquals(CouponStatus.ACTIVE, entity.getStatus());
        assertFalse(entity.isPublished());
        assertFalse(entity.isDeleted());
        assertFalse(entity.isRedeemed());
    }

    @Test
    void shouldReturnNullResponseWhenEntityIsNull() {
        assertNull(mapper.convertToResponse(null));
    }

    @Test
    void shouldConvertEntityToResponseKeepingStatus() {
        UUID id = UUID.randomUUID();
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(2);
        CouponEntity entity = new CouponEntity();
        entity.setId(id);
        entity.setCode("ABC123");
        entity.setDescription("Cupom de desconto");
        entity.setDiscountValue(10.0);
        entity.setExpirationDate(expirationDate);
        entity.setPublished(true);
        entity.setRedeemed(true);
        entity.setStatus(CouponStatus.INACTIVE);
        
        CouponResponse response = mapper.convertToResponse(entity);

        assertEquals(id, response.getId());
        assertEquals("ABC123", response.getCode());
        assertEquals("Cupom de desconto", response.getDescription());
        assertEquals(10.0, response.getDiscountValue());
        assertEquals(expirationDate, response.getExpirationDate());
        assertTrue(response.isPublished());
        assertTrue(response.isRedeemed());
        assertEquals(CouponStatus.INACTIVE, response.getStatus());
    }
}
