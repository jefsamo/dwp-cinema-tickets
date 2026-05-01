package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidAccountIDException;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TicketServiceImplTest {
    private TicketPaymentService paymentService;
    private SeatReservationService seatReservationService;
    private TicketServiceImpl ticketService;

    @BeforeEach
    void setUp() {
        paymentService = mock(TicketPaymentService.class);
        seatReservationService = mock(SeatReservationService.class);
        ticketService = new TicketServiceImpl(paymentService, seatReservationService);
    }

    @Test
    void shouldTakePaymentAndReserveSeatsForAdultTickets() {
        ticketService.purchaseTickets(
                1L,
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2)
        );

        verify(paymentService).makePayment(1L, 50);
        verify(seatReservationService).reserveSeat(1L, 2);
    }

    @Test
    void shouldCalculateCorrectPaymentAndSeatsForMixedTickets() {
        ticketService.purchaseTickets(
                1L,
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 3),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1)
        );

        verify(paymentService).makePayment(1L, 95);
        verify(seatReservationService).reserveSeat(1L, 5);
    }

    @Test
    void shouldRejectInvalidPurchaseAndNotCallExternalServices() {
        assertThrows(InvalidPurchaseException.class, () ->
                ticketService.purchaseTickets(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1)
                )
        );

        verifyNoInteractions(paymentService);
        verifyNoInteractions(seatReservationService);
    }

    @Test
    void shouldRejectMoreThanTwentyFiveTicketsAndNotCallExternalServices() {
        assertThrows(InvalidPurchaseException.class, () ->
                ticketService.purchaseTickets(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 26)
                )
        );

        verifyNoInteractions(paymentService);
        verifyNoInteractions(seatReservationService);
    }

    @Test
    void shouldRejectInvalidAccountAndNotCallExternalServices() {
        assertThrows(InvalidAccountIDException.class, () ->
                ticketService.purchaseTickets(
                        0L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1)
                )
        );

        verifyNoInteractions(paymentService);
        verifyNoInteractions(seatReservationService);
    }

    @Test
    void shouldRejectMoreInfantsThanAdultsAndNotCallExternalServices() {
        assertThrows(InvalidPurchaseException.class, () ->
                ticketService.purchaseTickets(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1),
                        new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2)
                )
        );

        verifyNoInteractions(paymentService);
        verifyNoInteractions(seatReservationService);
    }
}
