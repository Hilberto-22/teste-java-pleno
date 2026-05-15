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
public class CounponService {

    private final CouponRepository repository;
    private final CouponMapper mapper;

    public CouponResponse create(CouponRequest request) {
        Coupon coupon = new Coupon(
                request.getCode(),
                request.getDescription(),
                request.getDiscountValue(),
                request.getExpirationDate(),
                request.getPublished()
        );
        CouponEntity entity = mapper.convertToEntity(coupon);
        return mapper.convertToResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        CouponEntity entity = findById(id);
        if (entity.getDeleted()) {
            throw new ApiException("Coupon already deleted", HttpStatus.NOT_FOUND, "Coupon already deleted");
        }
        entity.setDeleted(true);
        repository.save(entity);
    }

    private CouponEntity findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ApiException("Coupon not found", HttpStatus.NOT_FOUND, "Coupon not found"));
    }
}
