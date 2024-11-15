package org.hsa.airdnd;

import org.hsa.airdnd.dto.BookingDto;
import org.hsa.airdnd.helpers.MailHelper;
import org.hsa.airdnd.repositories.BookingRepository;
import org.hsa.airdnd.repositories.PaymentRepository;
import org.hsa.airdnd.repositories.RoomRepository;
import org.hsa.airdnd.services.BookingService;
import org.hsa.airdnd.services.PaymentService;
import org.hsa.airdnd.services.RoomService;

import java.time.LocalDate;
import java.util.UUID;
public class Main {
    public static void main(String[] args) {
        final var paymentService = new PaymentService(new PaymentRepository());
        final var roomService = new RoomService(new RoomRepository());
        final var bookingRepository = new BookingRepository();
        final var mailHelper = new MailHelper();
        final var bookingService = new BookingService(
                paymentService,
                roomService,
                bookingRepository,
                mailHelper
        );

        var randomId = UUID.randomUUID().toString();
        var bookingDto = new BookingDto(
                randomId,
                LocalDate.of(2023, 06, 10),
                LocalDate.of(2023, 06, 20),
                2,
                true
        );
        var bookingResult = bookingService.booking(bookingDto);
        System.out.println(bookingResult);

        bookingService.unbook(bookingResult);

        var price = bookingService.calculatePrice(bookingDto);

        System.out.println(price);

        var priceMxn = bookingService.calculateInMxn(bookingDto);
        System.out.println(priceMxn);

        var roomsAvailable = bookingService.getAvailablePlaceCount();

        System.out.println(roomsAvailable);
    }
}