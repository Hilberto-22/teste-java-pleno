package com.hilberto.teste.domain.service;

import com.hilberto.teste.domain.dto.request.CouponRequest;
import com.hilberto.teste.domain.dto.response.CouponResponse;
import com.hilberto.teste.domain.enums.CouponStatus;
import com.hilberto.teste.infrastructure.exception.ApiException;
import com.hilberto.teste.infrastructure.persistence.entity.CouponEntity;
import com.hilberto.teste.infrastructure.persistence.mapper.CouponMapper;
import com.hilberto.teste.infrastructure.persistence.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private CouponRepository repository;

    private CouponService service;

    @BeforeEach
    void setUp() {
        service = new CouponService(repository, new CouponMapper());
    }

    @Test
    void shouldCreateCouponAndReturnResponse() {
        UUID id = UUID.randomUUID();
        CouponRequest request = new CouponRequest();
        request.setCode("AB-12_34");
        request.setDescription("Cupom de desconto");
        request.setDiscountValue(10.0);
        request.setExpirationDate(LocalDateTime.now().plusDays(1));

        when(repository.save(any(CouponEntity.class))).thenAnswer(invocation -> {
            CouponEntity entity = invocation.getArgument(0);
            entity.setId(id);
            return entity;
        });

        CouponResponse response = service.create(request);

        ArgumentCaptor<CouponEntity> entityCaptor = ArgumentCaptor.forClass(CouponEntity.class);
        verify(repository).save(entityCaptor.capture());

        assertEquals("AB1234", entityCaptor.getValue().getCode());
        assertEquals("Cupom de desconto", entityCaptor.getValue().getDescription());
        assertEquals(CouponStatus.ACTIVE, entityCaptor.getValue().getStatus());
        assertEquals(id, response.getId());
        assertEquals("AB1234", response.getCode());
        assertEquals(CouponStatus.ACTIVE, response.getStatus());
    }

    @Test
    void shouldDeleteCouponWhenItExists() {
        UUID id = UUID.randomUUID();
        CouponEntity entity = new CouponEntity();
        entity.setId(id);
        entity.setCode("ABC123");
        entity.setDescription("Cupom de desconto");
        entity.setDiscountValue(10.0);
        entity.setExpirationDate(LocalDateTime.now().plusDays(1));
        entity.setStatus(CouponStatus.ACTIVE);
        entity.setDeleted(false);

        when(repository.findByIdIncludingDeleted(id)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        service.delete(id);

        assertTrue(entity.isDeleted());
        verify(repository).save(entity);
    }

    @Test
    void shouldThrowWhenDeletingAlreadyDeletedCoupon() {
        UUID id = UUID.randomUUID();
        CouponEntity entity = new CouponEntity();
        entity.setId(id);
        entity.setDeleted(true);

        when(repository.findByIdIncludingDeleted(id)).thenReturn(Optional.of(entity));

        ApiException exception = assertThrows(ApiException.class, () -> service.delete(id));

        assertEquals("Coupon already deleted", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(repository, never()).save(any(CouponEntity.class));
    }

    @Test
    void shouldThrowWhenDeletingUnknownCoupon() {
        UUID id = UUID.randomUUID();
        when(repository.findByIdIncludingDeleted(id)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> service.delete(id));

        assertEquals("Coupon not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(repository, never()).save(any(CouponEntity.class));
    }

    @Test
    void shouldGetCouponById() {
        UUID id = UUID.randomUUID();
        CouponEntity entity = new CouponEntity();
        entity.setId(id);
        entity.setCode("ABC123");
        entity.setDescription("Cupom de desconto");
        entity.setDiscountValue(10.0);
        entity.setExpirationDate(LocalDateTime.now().plusDays(1));
        entity.setStatus(CouponStatus.INACTIVE);
        entity.setPublished(true);
        entity.setRedeemed(true);

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        CouponResponse response = service.getByCouponFromId(id);

        assertEquals(id, response.getId());
        assertEquals("ABC123", response.getCode());
        assertEquals(CouponStatus.INACTIVE, response.getStatus());
        assertTrue(response.isPublished());
        assertTrue(response.isRedeemed());
    }
}
