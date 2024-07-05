package net.celloscope.api.passwordresetlog.application.service;

import net.celloscope.api.OAuthHelper;
import net.celloscope.api.passwordresetlog.application.port.out.PasswordResetLogPersistence;
import net.celloscope.api.passwordresetlog.domain.PasswordResetLog;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PasswordResetServiceIntegrationTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    PasswordResetService passwordResetService;

    @Container
    public static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:11.7-alpine")
            .withDatabaseName("bkb")
            .withUsername("bkb")
            .withPassword("bkb")
            .withInitScript("password-reset-log.sql")
            .withReuse(true);

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Test
    void contextLoading() {
        Assertions.assertNotNull(PasswordResetService.class);
    }

    @ParameterizedTest
    @MethodSource("provideUserIdAndOldPasswordAndNewPassword")
    @Order(1)
    void createNewPasswordResetForUser(String userId, String oldPassword, String newPassword) {
        assertNotNull(passwordResetService.createNewPasswordResetForUser(userId, getHashedPasswordFromRawPassword(oldPassword), getHashedPasswordFromRawPassword(newPassword)));
    }

    @ParameterizedTest
    @MethodSource("provideUserIdAndNewPassword")
    @Order(3)
    void isNewPasswordMatchedWithLastFewPassword(String userId, String newPassword, Boolean result) {
        assertEquals(passwordResetService.isNewPasswordMatchedWithLastFewPassword(userId, newPassword), result);
    }

    private static Stream<Arguments> provideUserIdAndNewPassword() {
        return Stream.of(
                Arguments.of("01844074058", "password-new-a", false),
                Arguments.of("amiya.tisha.977484121", "password-tic-toc", false),
                Arguments.of("amiya.tisha.611265452", "password-new-c", true),
                Arguments.of("identity.verifier.sprint.naumy", "password-new-e", true)
        );
    }

    private static Stream<Arguments> provideUserIdAndOldPasswordAndNewPassword() {
        return Stream.of(
                Arguments.of("01844074058", "password-5", "password-6"),
                Arguments.of("01844074058", "password-4", "password-5"),
                Arguments.of("01844074058", "password-3", "password-4"),
                Arguments.of("01844074058", "password-2", "password-3"),
                Arguments.of("01844074058", "password-1", "password-2"),
                Arguments.of("amiya.tisha.977484121", "password-old-b", "password-new-b"),
                Arguments.of("amiya.tisha.611265452", "password-old-c", "password-new-c"),
                Arguments.of("amiya.tisha.109493551", "password-old-d", "password-new-d"),
                Arguments.of("identity.verifier.sprint.naumy", "password-old-e", "password-new-e")
        );
    }

    private String getHashedPasswordFromRawPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

}