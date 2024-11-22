package org.hsa.airdnd.services;

import org.hsa.airdnd.dto.BookingDto;
import org.hsa.airdnd.helpers.MailHelper;
import org.hsa.airdnd.repositories.BookingRepository;
import org.hsa.airdnd.services.util.DataMother;
import org.hsa.airdnd.utils.CurrencyConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    @InjectMocks
    private BookingService sutBookingService;
    @Mock
    private PaymentService paymentServiceMock;
    @Mock
    private RoomService roomServiceMock;
    @Mock
    private BookingRepository bookingRepositoryMock;
    @Mock
    private MailHelper mailHelperMock;
    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @Test
    @DisplayName("getAvailablePlaceCount should works")
    void testGetAvailablePlaceCount() {
       var expected = 10;
       doReturn(DataMother.available_ten_rooms).when(roomServiceMock).findAllAvailableRooms();
       var result = sutBookingService.getAvailablePlaceCount();

       assertEquals(expected, result);
    }

    @Test
    @DisplayName("booking happy path should works ")
    void tesBooking() {
        final var expected = UUID.randomUUID().toString();
        doReturn(DataMother.default_rooms.get(0)).when(roomServiceMock).findAvailableRoom(DataMother.default_booking_request_1);
        doReturn(expected).when(bookingRepositoryMock).save(DataMother.default_booking_request_1);

        var result = sutBookingService.booking(DataMother.default_booking_request_1);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("booking happy path should works - not import parameters")
    void tesBookingNotImportParam() {
        final var expected = UUID.randomUUID().toString();
        //No se llama el método real
        doReturn(DataMother.default_rooms.get(0)).when(roomServiceMock).findAvailableRoom(any(BookingDto.class));
        //Se llama el método real
        when(bookingRepositoryMock.save(any(BookingDto.class))).thenReturn(expected);
        //No se llama el método real
        doNothing().when(roomServiceMock).bookRoom(any(String.class));
        doNothing().when(mailHelperMock).sendMail(anyString(), anyString());

        var result = sutBookingService.booking(DataMother.default_booking_request_2);

        assertEquals(expected, result);
        verify(roomServiceMock).findAvailableRoom(any(BookingDto.class));
        verify(roomServiceMock).bookRoom(any(String.class));
        verify(mailHelperMock).sendMail(anyString(), anyString());
        verify(paymentServiceMock, never()).pay(any(BookingDto.class), anyDouble());
        verify(bookingRepositoryMock, times(1)).save(any(BookingDto.class));
    }

    @Test
    @DisplayName("booking unhappy path should works - not import parameters")
    void tesBookingWithParam() {
        doReturn(DataMother.default_rooms.get(0)).when(roomServiceMock).findAvailableRoom(DataMother.default_booking_request_3_isPrepaid_True);
        doThrow(new IllegalArgumentException("Max 3 guest)")).when(paymentServiceMock).pay(any(BookingDto.class), anyDouble());

        Executable executable = ()-> sutBookingService.booking(DataMother.default_booking_request_3_isPrepaid_True);

        assertThrows(IllegalArgumentException.class, executable);
        verify(roomServiceMock).findAvailableRoom(any(BookingDto.class));
        verify(roomServiceMock, never()).bookRoom(any(String.class));
        verify(mailHelperMock, never()).sendMail(anyString(), anyString());
        verify(paymentServiceMock).pay(any(BookingDto.class), anyDouble());
        verify(bookingRepositoryMock,never()).save(any(BookingDto.class));
    }

    @Test
    @DisplayName("GetAvailablePlaceCount should works")
    void testAvailablePlaceCount() {
        var expected = 10;
        var expectedEmpty = 0;
        //Mock with multiple returns
        when(roomServiceMock.findAllAvailableRooms())
                .thenReturn(DataMother.available_ten_rooms)
                .thenReturn(Collections.emptyList());

        var result = sutBookingService.getAvailablePlaceCount();
        var resultEmpty = sutBookingService.getAvailablePlaceCount();

        assertAll(
                () -> assertEquals(expected, result),
                () -> assertEquals(expectedEmpty, resultEmpty)
        );

        //It also works this way
        //assertEquals(expected, result);
        //assertEquals(expectedEmpty, resultEmpty);

    }

    @Test
    @DisplayName("testUnbook should works")
    void testUnbook() {
        var bookingID = UUID.randomUUID().toString();

        doReturn(DataMother.default_booking_response()).when(bookingRepositoryMock).findById(bookingID);
        doNothing().when(roomServiceMock).unbookRoom(any(String.class));
        doNothing().when(bookingRepositoryMock).deleteById(bookingID);

        sutBookingService.unbook(bookingID);

        verify(bookingRepositoryMock).findById(bookingID);
        verify(roomServiceMock).unbookRoom(any(String.class));
        verify(bookingRepositoryMock).deleteById(bookingID);
    }

    @Test
    @DisplayName("testUnbook should works")
    void testUnbook2() {
        var bookingID = UUID.randomUUID().toString();
        var bookingResponse = DataMother.default_booking_response();

        doReturn(bookingResponse).when(bookingRepositoryMock).findById(bookingID);
        doNothing().when(roomServiceMock).unbookRoom(bookingResponse.getRoom().getId());
        doNothing().when(bookingRepositoryMock).deleteById(bookingID);

        sutBookingService.unbook(bookingID);

        verify(bookingRepositoryMock).findById(bookingID);
        verify(roomServiceMock).unbookRoom(bookingResponse.getRoom().getId());
        verify(bookingRepositoryMock).deleteById(bookingID);

    }

    @Test
    @DisplayName("testUnbook should works - arguments capture")
    void testUnbook3() {
        var bookingID = UUID.randomUUID().toString();
        var bookingResponse = DataMother.default_booking_response();

        doReturn(bookingResponse).when(bookingRepositoryMock).findById(bookingID);
        doNothing().when(roomServiceMock).unbookRoom(bookingResponse.getRoom().getId());
        doNothing().when(bookingRepositoryMock).deleteById(stringCaptor.capture());

        sutBookingService.unbook(bookingID);

        //Capture argument
        assertEquals(List.of(bookingID),stringCaptor.getAllValues());

        verify(bookingRepositoryMock).findById(bookingID);
        verify(roomServiceMock).unbookRoom(bookingResponse.getRoom().getId());
        verify(bookingRepositoryMock).deleteById(bookingID);

        System.out.println("bookingID:" + bookingID + ", Capture argument:" + stringCaptor.getAllValues());

    }

    @Test
    @DisplayName("calculateInMxn should works")
    void testCalculateInMxn() {

         try(MockedStatic<CurrencyConverter> mockCurrencyConverter = mockStatic(CurrencyConverter.class)){

             final var  expected = 9000.0;
             var booking = DataMother.default_booking_response();
             mockCurrencyConverter.when(()-> CurrencyConverter.toMx(anyDouble())).thenReturn(expected);

             var result = sutBookingService.calculateInMxn(booking);

             assertEquals(expected, result);
         }
    }

    @Test
    @DisplayName("calculatePrice should works")
    void calculatePrice() {
        final var  expected = 200.0;
        var booking = DataMother.default_booking_response();

        var result = sutBookingService.calculatePrice(booking);

        assertEquals(expected, result);
    }
}
