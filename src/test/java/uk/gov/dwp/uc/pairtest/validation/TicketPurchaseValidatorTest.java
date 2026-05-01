package uk.gov.dwp.uc.pairtest.validation;

import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidAccountIDException;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TicketPurchaseValidatorTest {
    private final TicketPurchaseValidator validator = new TicketPurchaseValidator();

    @Test
    void shouldAllowValidAdultOnlyPurchase() {
        assertDoesNotThrow(() ->
                validator.validate(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2)
                )
        );
    }

    @Test
    void shouldAllowValidAdultChildAndInfantPurchase() {
        assertDoesNotThrow(() ->
                validator.validate(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2),
                        new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2),
                        new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1)
                )
        );
    }

    @Test
    void shouldRejectNullAccountId() {
        assertThrows(InvalidAccountIDException.class, () ->
                validator.validate(
                        null,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1)
                )
        );
    }

    @Test
    void shouldRejectZeroAccountId() {
        assertThrows(InvalidAccountIDException.class, () ->
                validator.validate(
                        0L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1)
                )
        );
    }

    @Test
    void shouldRejectNegativeAccountId() {
        assertThrows(InvalidAccountIDException.class, () ->
                validator.validate(
                        -1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1)
                )
        );
    }

    @Test
    void shouldRejectNoTicketRequests() {
        assertThrows(InvalidPurchaseException.class, () ->
                validator.validate(1L)
        );
    }

    @Test
    void shouldRejectNullTicketRequestsArray() {
        assertThrows(InvalidPurchaseException.class, () ->
                validator.validate(1L, (TicketTypeRequest[]) null)
        );
    }

    @Test
    void shouldRejectNullTicketRequest() {
        assertThrows(InvalidPurchaseException.class, () ->
                validator.validate(1L, (TicketTypeRequest) null)
        );
    }

    @Test
    void shouldRejectNullTicketType() {
        assertThrows(InvalidPurchaseException.class, () ->
                validator.validate(
                        1L,
                        new TicketTypeRequest(null, 1)
                )
        );
    }

    @Test
    void shouldRejectZeroTickets() {
        assertThrows(InvalidPurchaseException.class, () ->
                validator.validate(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 0)
                )
        );
    }

    @Test
    void shouldRejectNegativeTickets() {
        assertThrows(InvalidPurchaseException.class, () ->
                validator.validate(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, -1)
                )
        );
    }

    @Test
    void shouldRejectMoreThanTwentyFiveTickets() {
        assertThrows(InvalidPurchaseException.class, () ->
                validator.validate(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 26)
                )
        );
    }

    @Test
    void shouldRejectChildWithoutAdult() {
        assertThrows(InvalidPurchaseException.class, () ->
                validator.validate(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1)
                )
        );
    }

    @Test
    void shouldRejectInfantWithoutAdult() {
        assertThrows(InvalidPurchaseException.class, () ->
                validator.validate(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1)
                )
        );
    }

    @Test
    void shouldRejectChildAndInfantWithoutAdult() {
        assertThrows(InvalidPurchaseException.class, () ->
                validator.validate(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1),
                        new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1)
                )
        );
    }

    @Test
    void shouldRejectMoreInfantsThanAdults() {
        assertThrows(InvalidPurchaseException.class, () ->
                validator.validate(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1),
                        new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2)
                )
        );
    }
}
