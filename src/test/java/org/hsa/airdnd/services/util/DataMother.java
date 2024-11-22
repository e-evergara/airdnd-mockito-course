package org.hsa.airdnd.services.util;

import org.hsa.airdnd.dto.BookingDto;
import org.hsa.airdnd.dto.RoomDto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataMother {

    private DataMother(){}

    public static final Map<RoomDto, Boolean> rooms_default = new HashMap<>() {{
        put(new RoomDto("1a", 2), true);
        put(new RoomDto("1b", 2), true);
        put(new RoomDto("1c", 3), false);
        put(new RoomDto("2a", 2), true);
        put(new RoomDto("2b", 2), false);
        put(new RoomDto("2c", 3), true);
    }};

    public static final Map<RoomDto, Boolean> four_availableRooms = new HashMap<>() {{
        put(new RoomDto("1a", 2), true);
        put(new RoomDto("1b", 2), true);
        put(new RoomDto("2a", 2), true);
        put(new RoomDto("2b", 3), true);
    }};

    public static final List<RoomDto> default_rooms = List.of(
            new RoomDto("1a", 2)
    );

    public static final List<RoomDto> available_ten_rooms = List.of(
        new RoomDto("1a", 2),
        new RoomDto("1b", 2),
        new RoomDto("1c", 3),
        new RoomDto("2c", 3)
    );

    public static final BookingDto default_booking_request_1 = new BookingDto(
            "12345",
            LocalDate.of(2023, 6, 10),
            LocalDate.of(2023, 6, 20),
            2,
            false
    );

    public static final BookingDto default_booking_request_2 = new BookingDto(
            "12346",
            LocalDate.of(2023, 7, 10),
            LocalDate.of(2023, 7, 20),
            2,
            false
    );

    public static final BookingDto default_booking_request_3_isPrepaid_True = new BookingDto(
            "12347",
            LocalDate.of(2023, 8, 10),
            LocalDate.of(2023, 8, 20),
            2,
            true
    );

    public static BookingDto default_booking_response(){
        var defaultBookingResponse = default_booking_request_1;
        defaultBookingResponse.setRoom(default_rooms.get(0));
        return defaultBookingResponse;
    }

}