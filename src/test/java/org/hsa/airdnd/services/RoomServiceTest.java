package org.hsa.airdnd.services;

import org.hsa.airdnd.repositories.RoomRepository;
import org.hsa.airdnd.services.util.DataMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @InjectMocks
    private RoomService sutRoomService;
    @Mock
    private RoomRepository roomRepositoryMock;


    @Test
    @DisplayName("Should get all rooms available in the room repository")
    void testFindAllAvailableRooms(){
        var expected = 4;

        doReturn(DataMother.four_availableRooms).when(roomRepositoryMock).findAll();
        var result = sutRoomService.findAllAvailableRooms();

        assertEquals(expected, result.size());

    }
}