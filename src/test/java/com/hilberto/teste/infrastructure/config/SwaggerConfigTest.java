package com.hilberto.teste.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SwaggerConfigTest {

    @Test
    void shouldBuildOpenApiMetadata() {
        SwaggerConfig config = new SwaggerConfig();

        OpenAPI openAPI = config.customOpenAPI();

        assertNotNull(openAPI.getInfo());
        assertTrue(openAPI.getInfo().getTitle().startsWith("Teste"));
        assertEquals("v1", openAPI.getInfo().getVersion());
        assertEquals("http://springdoc.org", openAPI.getInfo().getLicense().getUrl());
    }
}
