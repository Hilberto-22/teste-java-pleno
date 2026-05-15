package com.hilberto.teste.infrastructure.persistence.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.hilberto.teste.domain.CouponStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLRestriction("deleted = false")
public class CouponEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String code;
    private String description;
    private Double discountValue;
    private LocalDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    private CouponStatus status;
    private boolean published = false;
    private boolean deleted = false;
    private boolean redeemed = false;
}
