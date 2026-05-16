package com.hilberto.teste.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WebConfigTest {

    @Test
    void shouldRegisterCorsMapping() throws Exception {
        WebConfig config = new WebConfig();
        CorsRegistry registry = new CorsRegistry();

        config.addCorsMappings(registry);

        Field registrationsField = CorsRegistry.class.getDeclaredField("registrations");
        registrationsField.setAccessible(true);
        List<?> registrations = (List<?>) registrationsField.get(registry);

        assertEquals(1, registrations.size());
    }
}
