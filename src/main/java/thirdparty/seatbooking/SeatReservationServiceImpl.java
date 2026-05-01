package thirdparty.seatbooking;

public class SeatReservationServiceImpl implements SeatReservationService {

    @Override
    public void reserveSeat(long accountId, int totalSeatsToAllocate) {
        // Real implementation omitted, assume working code will make the seat reservation.
        validateAccountId(accountId);
        validateSeatCount(totalSeatsToAllocate);
    }

    private void validateAccountId(long accountId) {
        if (accountId <= 0) {
            throw new IllegalArgumentException("Invalid account id.");
        }
    }

    private void validateSeatCount(int seats) {
        if (seats <= 0) {
            throw new IllegalArgumentException("Seat count must be positive.");
        }
    }

}
