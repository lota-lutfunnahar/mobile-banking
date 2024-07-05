package net.celloscope.api.passwordresetlog.application.port.out;

import net.celloscope.api.passwordresetlog.application.port.in.PasswordResetUseCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordResetLogPersistenceTest {

    @Test
    void contextLoading() {
        assertNotNull(PasswordResetUseCase.class);
    }

}