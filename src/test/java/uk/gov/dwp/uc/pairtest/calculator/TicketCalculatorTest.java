package uk.gov.dwp.uc.pairtest.calculator;

import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketCalculatorTest {

    private final TicketCalculator calculator = new TicketCalculator();

    @Test
    void shouldCalculatePaymentForAdultTicketsOnly() {
        int result = calculator.calculatePaymentAmount(
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2)
        );

        assertEquals(50, result);
    }

    @Test
    void shouldCalculatePaymentForAdultsChildrenAndInfants() {
        int result = calculator.calculatePaymentAmount(
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 3),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2)
        );

        assertEquals(95, result);
    }

    @Test
    void shouldCalculateSeatsForAdultsAndChildrenOnly() {
        int result = calculator.calculateSeatsToReserve(
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 3),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2)
        );

        assertEquals(5, result);
    }

    @Test
    void shouldCountTicketsByType() {
        int result = calculator.countTickets(
                new TicketTypeRequest[]{
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2),
                        new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 3),
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1)
                },
                TicketTypeRequest.Type.ADULT
        );

        assertEquals(3, result);
    }
}
