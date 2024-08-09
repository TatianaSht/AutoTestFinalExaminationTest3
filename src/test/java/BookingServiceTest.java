import autoTest.BookingService;
import autoTest.CantBookException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceTest.class);

    private BookingService bookingService;
    private AutoCloseable closeable;
    private final String userId = "Ivanov";
    private final LocalDateTime from = LocalDateTime.of(2024, 8, 22, 10, 0);
    private final LocalDateTime to = LocalDateTime.of(2024, 8, 22, 14, 0);


    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        bookingService = Mockito.spy(new BookingService());
    }


    @Test
    void successfulBookingOfATimeSlotTest() throws CantBookException {
        logger.debug("Start mock");
        logger.debug("Successful booking test started");
        Mockito.when(bookingService.checkTimeInBD(from, to)).thenReturn(true);
        Mockito.when(bookingService.createBook(userId, from, to)).thenReturn(true);
        Mockito.when(bookingService.book(userId, from, to)).thenReturn(true);
        Assertions.assertTrue(bookingService.book(userId, from, to));
        logger.info("Reservation created");
        Mockito.verify(bookingService, times(2)).book(userId, from, to);
        logger.debug("Stop mock");
    }

    @Test
    void unsuccessfulBookingOfATimeSlotTest() throws CantBookException {
        logger.debug("Start mock");
        logger.debug("Unsuccessful booking test started");
        Mockito.when(bookingService.checkTimeInBD(from, to)).thenReturn(false);
        assertThrows(CantBookException.class, () -> bookingService.book(userId, from, to));
        Assertions.assertFalse(bookingService.checkTimeInBD(from, to));
        logger.info("Reservation not created. Throw CantBookException");
        Mockito.verify(bookingService, times(1)).book(userId, from, to);
        logger.debug("Stop mock");
    }

    @AfterEach
    public void closeApp() throws Exception {
        closeable.close();
    }
}