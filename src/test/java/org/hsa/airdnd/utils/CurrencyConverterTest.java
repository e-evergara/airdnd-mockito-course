package org.hsa.airdnd.utils;

import org.hsa.airdnd.services.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CurrencyConverterTest {
    @InjectMocks
    private CurrencyConverter sutCurrencyConverter;

    @Test
    @DisplayName("toMx should word")
    void ttoMx() {
        final double amount = 100.0;
        final double expected = amount * UtilMother.USD_TO_MX;

        var result = sutCurrencyConverter.toMx(amount);

        assertEquals(expected, result);
    }

}