package org.hsa.airdnd.helpers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MailHelperTest {
    @InjectMocks
    private MailHelper sutMailHelper;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @Test
    @DisplayName("sendMail should to work")
    void testSendMail() {
        final String bookingId = "12345";
        final String recipient = "debuggeandoideas@gmail2.com";
        sutMailHelper.sendMail(bookingId, recipient);
    }

    @Test
    @DisplayName("sendMail should to work")
    void testSendMailException() {
        final String bookingId = "12345";
        final String recipient = null;
        Executable executable = ()-> sutMailHelper.sendMail(bookingId, recipient);

        assertThrows(IllegalArgumentException.class, executable);

    }
}