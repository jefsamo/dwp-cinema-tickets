package uk.gov.dwp.uc.pairtest.calculator;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

public class TicketCalculator {

    private static final int ADULT_TICKET_PRICE = 25;
    private static final int CHILD_TICKET_PRICE = 15;

    public int calculatePaymentAmount(TicketTypeRequest... requests) {
        int adultTickets = countTickets(requests, TicketTypeRequest.Type.ADULT);
        int childTickets = countTickets(requests, TicketTypeRequest.Type.CHILD);

        return (adultTickets * ADULT_TICKET_PRICE) + (childTickets * CHILD_TICKET_PRICE);
    }

    public int calculateSeatsToReserve(TicketTypeRequest... requests) {
        int adultTickets = countTickets(requests, TicketTypeRequest.Type.ADULT);
        int childTickets = countTickets(requests, TicketTypeRequest.Type.CHILD);

        return adultTickets + childTickets;
    }

    public int countTickets(TicketTypeRequest[] requests, TicketTypeRequest.Type type) {
        int total = 0;

        for (TicketTypeRequest request : requests) {
            if (request.getTicketType() == type) {
                total += request.getNoOfTickets();
            }
        }

        return total;
    }
}
