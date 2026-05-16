package com.hilberto.teste.resources.controller;

import com.hilberto.teste.domain.dto.request.CouponRequest;
import com.hilberto.teste.domain.dto.response.CouponResponse;
import com.hilberto.teste.domain.service.CouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/coupon")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService){
        this.couponService = couponService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CouponResponse> createCoupon(@RequestBody CouponRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(couponService.create(request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        couponService.delete(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CouponResponse> getByCouponFromId(@PathVariable UUID id){
        return ResponseEntity.ok(couponService.getByCouponFromId(id));
    }
}
