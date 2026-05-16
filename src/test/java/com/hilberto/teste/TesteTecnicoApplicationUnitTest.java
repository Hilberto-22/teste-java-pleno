package com.hilberto.teste;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

class TesteTecnicoApplicationUnitTest {

    @Test
    void shouldDelegateToSpringApplicationRun() {
        String[] args = {"--spring.profiles.active=test"};

        try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class)) {
            TesteTecnicoApplication.main(args);

            springApplication.verify(() -> SpringApplication.run(TesteTecnicoApplication.class, args));
        }
    }
}
