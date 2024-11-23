package org.hsa.airdnd.services;

import org.hsa.airdnd.repositories.PaymentRepository;
import org.hsa.airdnd.services.util.DataMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class PaymentServiceTest {
    @InjectMocks
    private PaymentService sutPaymentService;
    @Mock
    private PaymentRepository paymentRepositoryMock;
    @Captor
    private ArgumentCaptor<String> stringCaptor;
    @Captor
    private ArgumentCaptor<Double> doubleaptor;

    @Test
    @DisplayName("Should save the payment")
    void testPay(){
        var bookingResponse = DataMother.default_booking_response();
        var price = 2000.0;
        var bookingID = UUID.randomUUID().toString();
        var fixedUUID = mock(UUID.class);

        try (MockedStatic<UUID> mockedUUID = mockStatic(UUID.class)) {
            mockedUUID.when(() -> UUID.randomUUID()).thenReturn(fixedUUID);
            doReturn(bookingID).when(fixedUUID).toString();
            doNothing().when(paymentRepositoryMock).save(bookingID, price);
            doReturn(price).when(paymentRepositoryMock).findById(bookingID);

            // Act
            double result = sutPaymentService.pay(bookingResponse, price);

            // Assert
            assertEquals(price, result);
            verify(paymentRepositoryMock).save(bookingID, price);
            verify(paymentRepositoryMock).findById(bookingID);
        }
    }

    @Test
    @DisplayName("Should not save the payment")
    void testPayArgumentException(){
        var bookingResponse = DataMother.default_booking_response();
        bookingResponse.setTotalGuest(4);
        var price = 2000.0;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sutPaymentService.pay(bookingResponse, price));

        assertEquals("Max 3 guest", exception.getMessage());
    }

}