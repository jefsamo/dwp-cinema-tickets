package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidAccountIDException;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class App {
    public static void main(String[] args) {

        TicketService ticketService = new TicketServiceImpl(
                new TicketPaymentServiceImpl(),
                new SeatReservationServiceImpl()
        );

        try {

            ticketService.purchaseTickets(
                    1L,
                    new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1),
                    new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1),
                    new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1),
                    new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1)
            );

            System.out.println("Ticket Purchase completed successfully.");

        } catch (InvalidAccountIDException ex) {
            System.out.println("Invalid account: " + ex.getMessage());
        }
        catch (InvalidPurchaseException ex) {
            System.out.println("Purchase failed: " + ex.getMessage());
        }
    }
}
