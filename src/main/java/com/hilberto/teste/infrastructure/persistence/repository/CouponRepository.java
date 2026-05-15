package com.hilberto.teste.infrastructure.persistence.repository;

import com.hilberto.teste.domain.model.Coupon;
import com.hilberto.teste.infrastructure.persistence.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, UUID> {
}
