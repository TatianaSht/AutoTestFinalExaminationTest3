package autoTest;

import java.time.LocalDateTime;

public class BookingService {

    public boolean book(String userId, LocalDateTime from, LocalDateTime to) throws CantBookException {
        if(checkTimeInBD(from, to)) {
            return createBook(userId,from, to);
        }
        throw new CantBookException();
    }


    public boolean checkTimeInBD(LocalDateTime from, LocalDateTime to) {
        return false;
    }


    public boolean createBook(String userId, LocalDateTime from, LocalDateTime to) {
        return false;
    }
}
