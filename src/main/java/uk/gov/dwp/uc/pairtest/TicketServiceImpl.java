package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.calculator.TicketCalculator;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.validation.TicketPurchaseValidator;

public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */


    private final TicketPaymentService ticketPaymentService;
    private final SeatReservationService seatReservationService;

    private final TicketPurchaseValidator validator;
    private final TicketCalculator calculator;


    public TicketServiceImpl(TicketPaymentService ticketPaymentService, SeatReservationService seatReservationService) {
        this.ticketPaymentService = ticketPaymentService;
        this.seatReservationService = seatReservationService;
        this.validator = new TicketPurchaseValidator();
        this.calculator =  new TicketCalculator();
    }

    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests)
            throws InvalidPurchaseException {

        validator.validate(accountId, ticketTypeRequests);

        int totalAmountToPay = calculator.calculatePaymentAmount(ticketTypeRequests);
        int totalSeatsToReserve = calculator.calculateSeatsToReserve(ticketTypeRequests);

        ticketPaymentService.makePayment(accountId, totalAmountToPay);
        seatReservationService.reserveSeat(accountId, totalSeatsToReserve);
    }


}
