package com.hilberto.teste.resources.controller;

import com.hilberto.teste.domain.dto.request.CouponRequest;
import com.hilberto.teste.domain.dto.response.CouponResponse;
import com.hilberto.teste.domain.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/coupon")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService){
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<CouponResponse> createCoupon(@RequestBody CouponRequest request){
        return ResponseEntity.ok(couponService.create(request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        couponService.delete(id);
    }
}
