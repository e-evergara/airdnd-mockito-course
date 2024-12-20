package org.hsa.airdnd.services;

import org.hsa.airdnd.dto.BookingDto;
import org.hsa.airdnd.dto.RoomDto;
import org.hsa.airdnd.helpers.MailHelper;
import org.hsa.airdnd.repositories.BookingRepository;
import org.hsa.airdnd.utils.CurrencyConverter;

import java.time.temporal.ChronoUnit;

public class BookingService {

    private final PaymentService paymentService;
    private final RoomService roomService;
    private final BookingRepository bookingRepository;
    private final MailHelper mailHelper;

    private static final double PRICE_ROOM = 20.00;

    public BookingService(PaymentService paymentService, RoomService roomService, BookingRepository bookingRepository, MailHelper mailHelper) {
        this.paymentService = paymentService;
        this.roomService = roomService;
        this.bookingRepository = bookingRepository;
        this.mailHelper = mailHelper;
    }

    public int getAvailablePlaceCount() {
        return this.roomService.findAllAvailableRooms().stream()
                .map(RoomDto::getCapacity)
                .reduce(0, Integer::sum);
    }

    public double calculatePrice(final BookingDto booking) {
        final long totalNights = getTotalNights(booking);
        return PRICE_ROOM * totalNights;
    }

    private static long getTotalNights(BookingDto booking) {
        return ChronoUnit.DAYS.between(booking.getFrom(), booking.getTo());
    }

    public double calculateInMxn(final BookingDto booking) {
        return CurrencyConverter.toMx(calculatePrice(booking));
    }

    public String booking(final BookingDto booking) {
        final RoomDto room = roomService.findAvailableRoom(booking);
        final String roomId = room.getId();
        final double price = this.calculatePrice(booking);

        if (booking.getPrepaid()) {
            this.paymentService.pay(booking, price);
        }

        booking.setRoom(room);
        final String bookingId = bookingRepository.save(booking);
        this.roomService.bookRoom(roomId);
        this.mailHelper.sendMail(bookingId, "debuggeandoideas@gmail2.com");
        return bookingId;
    }

    public void unbook(final String bookingId) {
        final BookingDto booking = this.bookingRepository.findById(bookingId);
        this.roomService.unbookRoom(booking.getRoom().getId());
        this.bookingRepository.deleteById(bookingId);
    }
}