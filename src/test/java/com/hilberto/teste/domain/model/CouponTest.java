package com.hilberto.teste.domain.model;

import com.hilberto.teste.infrastructure.exception.ApiException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CouponTest {

    @Test
    void shouldCreateCouponWithSanitizedCodeAndDefaultFlags() {
        Coupon coupon = new Coupon(
                "AB-12_34",
                "Cupom de desconto",
                10.0,
                LocalDateTime.now().plusDays(1),
                false);

        assertEquals("AB1234", coupon.getCode());
        assertEquals("Cupom de desconto", coupon.getDescription());
        assertEquals(10.0, coupon.getDiscountValue());
        assertFalse(coupon.isPublished());
        assertFalse(coupon.isDeleted());
        assertFalse(coupon.isRedeemed());
    }

    @Test
    void shouldThrowWhenCodeIsNull() {
        ApiException exception = assertThrows(ApiException.class, () -> new Coupon(
                null,
                "Cupom de desconto",
                10.0,
                LocalDateTime.now().plusDays(1),
                false));

        assertEquals("Code cannot be null", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertEquals("Code cannot be null", exception.getDetails());
    }

    @Test
    void shouldThrowWhenSanitizedCodeDoesNotHaveSixCharacters() {
        ApiException exception = assertThrows(ApiException.class, () -> new Coupon(
                "AB-123_456",
                "Cupom de desconto",
                10.0,
                LocalDateTime.now().plusDays(1),
                false));

        assertEquals("Code must have 6 characters", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    void shouldThrowWhenDescriptionIsNull() {
        ApiException exception = assertThrows(ApiException.class, () -> new Coupon(
                "ABC123",
                null,
                10.0,
                LocalDateTime.now().plusDays(1),
                false));

        assertEquals("The description is required", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void shouldThrowWhenDiscountIsLowerThanMinimum() {
        ApiException exception = assertThrows(ApiException.class, () -> new Coupon(
                "ABC123",
                "Cupom de desconto",
                0.4,
                LocalDateTime.now().plusDays(1),
                false));

        assertEquals("Discount value must be greater than 0.5", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    void shouldThrowWhenDiscountIsNull() {
        ApiException exception = assertThrows(ApiException.class, () -> new Coupon(
                "ABC123",
                "Cupom de desconto",
                null,
                LocalDateTime.now().plusDays(1),
                false));

        assertEquals("Discount value must be greater than 0.5", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    void shouldThrowWhenExpirationDateIsNull() {
        ApiException exception = assertThrows(ApiException.class, () -> new Coupon(
                "ABC123",
                "Cupom de desconto",
                10.0,
                null,
                false));

        assertEquals("Invalid expiration date", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    void shouldThrowWhenExpirationDateIsInThePast() {
        ApiException exception = assertThrows(ApiException.class, () -> new Coupon(
                "ABC123",
                "Cupom de desconto",
                10.0,
                LocalDateTime.now().minusMinutes(1),
                false));

        assertEquals("Invalid expiration date", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    void shouldMarkCouponAsDeleted() {
        Coupon coupon = new Coupon(
                "ABC123",
                "Cupom de desconto",
                10.0,
                LocalDateTime.now().plusDays(1),
                false);

        coupon.delete();

        assertTrue(coupon.isDeleted());
    }
}
