package org.hsa.airdnd.services;

import org.hsa.airdnd.repositories.RoomRepository;

import org.hsa.airdnd.services.util.DataMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RoomServiceTraditionalTest {

    private RoomService sutRoomService;
    private RoomRepository roomRepositoryMock;

    @BeforeEach
    void setUp() {
        this.roomRepositoryMock = mock(RoomRepository.class);
        this.sutRoomService = new RoomService(roomRepositoryMock);
    }

    @Test
    @DisplayName("Should get all rooms available in the room repository")
    void testFindAllAvailableRooms(){
        var expected = 4;

        when(roomRepositoryMock.findAll()).thenReturn(DataMother.four_availableRooms);
        var result = sutRoomService.findAllAvailableRooms();

        assertEquals(expected, result.size());

    }
}