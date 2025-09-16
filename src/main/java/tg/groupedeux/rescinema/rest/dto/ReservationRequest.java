package tg.groupedeux.rescinema.rest.dto;

import java.util.List;

public class ReservationRequest {
    public Long userId;
    public Long seanceId;
    public List<Seat> seats; // row, seat

    public static class Seat {
        public int row;
        public int seat;
    }
}
