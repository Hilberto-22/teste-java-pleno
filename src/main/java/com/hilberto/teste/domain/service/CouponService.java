package com.hilberto.teste.domain.service;

import com.hilberto.teste.domain.dto.request.CouponRequest;
import com.hilberto.teste.domain.dto.response.CouponResponse;
import com.hilberto.teste.domain.model.Coupon;
import com.hilberto.teste.infrastructure.exception.ApiException;
import com.hilberto.teste.infrastructure.persistence.entity.CouponEntity;
import com.hilberto.teste.infrastructure.persistence.mapper.CouponMapper;
import com.hilberto.teste.infrastructure.persistence.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository repository;
    private final CouponMapper mapper;

    public CouponResponse create(CouponRequest request) {
        Coupon coupon = new Coupon(
                request.getCode(),
                request.getDescription(),
                request.getDiscountValue(),
                request.getExpirationDate(),
                request.isPublished()
            );
        CouponEntity entity = mapper.convertToEntity(coupon);
        return mapper.convertToResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public CouponResponse getByCouponFromId(UUID id) {
        CouponEntity entity = findById(id);
        return mapper.convertToResponse(entity);
    }

    @Transactional
    public void delete(UUID id) {
        CouponEntity entity = findByIdIncludingDeleted(id);
        if (entity.isDeleted()) {
            throw new ApiException("Coupon already deleted", HttpStatus.BAD_REQUEST, "Coupon already deleted");
        }
        entity.setDeleted(true);
        repository.save(entity);
    }

    private CouponEntity findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApiException("Coupon not found", HttpStatus.NOT_FOUND, "Coupon not found"));
    }

    private CouponEntity findByIdIncludingDeleted(UUID id) {
        return repository.findByIdIncludingDeleted(id)
                .orElseThrow(() -> new ApiException("Coupon not found", HttpStatus.NOT_FOUND, "Coupon not found"));
    }
}
