


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class TestInit {

    private DefaulDataMock dataMock;

    @BeforeEach
    void setUp() {
        this.dataMock = mock(DefaulDataMock.class);
    }

    @Test
    void testBooking() {
        System.out.println("Booking test");
        assertTrue(true);
    }
    @Test
    void testDefaultValueMockito() {
        var collection = dataMock.findBookings();
        var isBoolean = dataMock.getBoolean();
        var myString = dataMock.getString();
        var myInteger = dataMock.getInteger();
        System.out.println("DATOS POR DEFECTO CUANDO NO LE DAMOS VALORES A LOS MOCK");
        System.out.println("Default collection: " + collection);
        System.out.println("Default boolean: " + isBoolean);
        System.out.println("Default String: " + myString);
        System.out.println("Default Integer: " + myInteger);
        assertTrue(true);
    }
}

class DefaulDataMock{

    public Map<String, String> findBookings() {
        return new HashMap<>() {{
            put("Hello", "World");

        }};
    }

    public void save(String key, String value) {
        System.out.println("Key: " + key + " Value: " + value);
    }

    public boolean getBoolean() {
        return true;
    }

    public String getString() {
        return "Hello";
    }

    public Integer getInteger() {
        return 10;
    }
}