package com.hilberto.teste.resources.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hilberto.teste.domain.CouponStatus;
import com.hilberto.teste.domain.dto.request.CouponRequest;
import com.hilberto.teste.domain.dto.response.CouponResponse;
import com.hilberto.teste.domain.service.CouponService;
import com.hilberto.teste.infrastructure.exception.ApiException;
import com.hilberto.teste.infrastructure.exception.ApiExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CouponControllerTest {

    @Mock
    private CouponService couponService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(new CouponController(couponService))
                .setControllerAdvice(new ApiExceptionHandler())
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    void shouldCreateCoupon() throws Exception {
        UUID id = UUID.randomUUID();
        CouponRequest request = new CouponRequest();
        request.setCode("ABC123");
        request.setDescription("Cupom de desconto");
        request.setDiscountValue(10.0);
        request.setExpirationDate(LocalDateTime.now().plusDays(1));

        CouponResponse response = new CouponResponse(
                id,
                "ABC123",
                "Cupom de desconto",
                10.0,
                request.getExpirationDate(),
                false,
                false,
                CouponStatus.ACTIVE);

        when(couponService.create(any(CouponRequest.class))).thenReturn(response);

        mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.code").value("ABC123"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(couponService).create(any(CouponRequest.class));
    }

    @Test
    void shouldDeleteCoupon() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(couponService).delete(id);

        mockMvc.perform(delete("/coupon/{id}", id))
                .andExpect(status().isNoContent());

        verify(couponService).delete(id);
    }

    @Test
    void shouldReturnAlreadyDeletedMessageWhenDeletingDeletedCoupon() throws Exception {
        UUID id = UUID.randomUUID();
        doThrow(new ApiException("Coupon already deleted", HttpStatus.BAD_REQUEST, "Coupon already deleted"))
                .when(couponService).delete(id);

        mockMvc.perform(delete("/coupon/{id}", id))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Coupon already deleted"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.details").value("Coupon already deleted"));
    }

    @Test
    void shouldGetCouponById() throws Exception {
        UUID id = UUID.randomUUID();
        CouponResponse response = new CouponResponse(
                id,
                "ABC123",
                "Cupom de desconto",
                10.0,
                LocalDateTime.now().plusDays(1),
                true,
                false,
                CouponStatus.INACTIVE);

        when(couponService.getByCouponFromId(id)).thenReturn(response);

        mockMvc.perform(get("/coupon/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.status").value("INACTIVE"))
                .andExpect(jsonPath("$.published").value(true));

        verify(couponService).getByCouponFromId(id);
    }

    @Test
    void shouldReturnHandledApiException() throws Exception {
        UUID id = UUID.randomUUID();
        doThrow(new ApiException("Coupon not found", HttpStatus.NOT_FOUND, "Coupon not found"))
                .when(couponService).delete(id);

        mockMvc.perform(delete("/coupon/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Coupon not found"))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.details").value("Coupon not found"));
    }

    @Test
    void shouldReturnHandledGeneralException() throws Exception {
        when(couponService.getByCouponFromId(any(UUID.class))).thenThrow(new RuntimeException("boom"));

        mockMvc.perform(get("/coupon/{id}", UUID.randomUUID()))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"))
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.details").value("boom"));
    }
}
