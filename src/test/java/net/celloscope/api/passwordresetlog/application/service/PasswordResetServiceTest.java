package net.celloscope.api.passwordresetlog.application.service;

import net.celloscope.api.passwordresetlog.application.port.out.PasswordResetLogPersistence;
import net.celloscope.api.passwordresetlog.domain.PasswordResetLog;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PasswordResetServiceTest {

    @Autowired
    PasswordResetService passwordResetService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    PasswordResetLogPersistence passwordResetLogPersistence;

    @Test
    void contextLoading() {
        Assertions.assertNotNull(PasswordResetService.class);
    }

    @ParameterizedTest
    @MethodSource("provideUserIdAndNewPassword")
    void isNewPasswordMatchedWithLastFewPassword(String userId, String newPassword, Boolean result) {
        int count = 5;
        mockGetLastFewPasswordsByUserIDAndCount(5);
        assertEquals(passwordResetService.isNewPasswordMatchedWithLastFewPassword(userId, newPassword), result);
    }

    private static Stream<Arguments> provideUserIdAndNewPassword() {
        return Stream.of(
                Arguments.of("a", "password-new-a", false),
                Arguments.of("b", "password-new-b", false),
                Arguments.of("c", "password-c", true),
                Arguments.of("d", "password-d", true)
        );
    }

    private String getHashedPasswordFromRawPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    private void mockGetLastFewPasswordsByUserIDAndCount(int count) {
        Mockito.when(passwordResetLogPersistence.getLastPasswordResetLogsByUserIdAndCountFromPersistence(Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(Arrays.asList(buildPasswordResetLogWithOldPasswordOnly(getHashedPasswordFromRawPassword("password-a")),
                        buildPasswordResetLogWithOldPasswordOnly(getHashedPasswordFromRawPassword("password-b")),
                        buildPasswordResetLogWithOldPasswordOnly(getHashedPasswordFromRawPassword("password-c")),
                        buildPasswordResetLogWithOldPasswordOnly(getHashedPasswordFromRawPassword("password-d")),
                        buildPasswordResetLogWithOldPasswordOnly(getHashedPasswordFromRawPassword("password-e"))));
    }

    private PasswordResetLog buildPasswordResetLogWithOldPasswordOnly(String newPassword) {
        return PasswordResetLog.builder()
                .newPassword(newPassword)
                .build();
    }

}