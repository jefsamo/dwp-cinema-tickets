package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidAccountIDException;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class TicketPurchaseValidator {
    private static final int MAX_TICKETS_PER_PURCHASE = 25;

    public void validate(Long accountId, TicketTypeRequest... ticketTypeRequests) {
        validateAccountId(accountId);
        validateTicketRequests(ticketTypeRequests);

        int adultTickets = countTickets(ticketTypeRequests, TicketTypeRequest.Type.ADULT);
        int childTickets = countTickets(ticketTypeRequests, TicketTypeRequest.Type.CHILD);
        int infantTickets = countTickets(ticketTypeRequests, TicketTypeRequest.Type.INFANT);

        int totalTickets = adultTickets + childTickets + infantTickets;

        validateTotalTickets(totalTickets);
        validateAdultRequirement(adultTickets, childTickets, infantTickets);
    }

    private void validateAccountId(Long accountId) {
        if (accountId == null || accountId <= 0) {
            throw new InvalidAccountIDException("Account id must be greater than zero.");
        }
    }

    private void validateTicketRequests(TicketTypeRequest... ticketTypeRequests) {
        if (ticketTypeRequests == null || ticketTypeRequests.length == 0) {
            throw new InvalidPurchaseException("At least one ticket request is required.");
        }

        for (TicketTypeRequest request : ticketTypeRequests) {
            if (request == null) {
                throw new InvalidPurchaseException("Ticket request cannot be null.");
            }

            if (request.getTicketType() == null) {
                throw new InvalidPurchaseException("Ticket type cannot be null.");
            }

            if (request.getNoOfTickets() <= 0) {
                throw new InvalidPurchaseException("Number of tickets must be greater than zero.");
            }
        }
    }

    private void validateTotalTickets(int totalTickets) {
        if (totalTickets > MAX_TICKETS_PER_PURCHASE) {
            throw new InvalidPurchaseException("Total tickets cannot be more than 25.");
        }
    }

    private void validateAdultRequirement(int adultTickets, int childTickets, int infantTickets) {
        boolean hasChildOrInfant = childTickets > 0 || infantTickets > 0;

        if (hasChildOrInfant && adultTickets == 0) {
            throw new InvalidPurchaseException("An adult must accompany a child or infant ticket.");
        }

        if (infantTickets > adultTickets) {
            throw new InvalidPurchaseException("Infant tickets cannot be more than adult tickets.");
        }
    }

    private int countTickets(TicketTypeRequest[] requests, TicketTypeRequest.Type type) {
        int total = 0;

        for (TicketTypeRequest request : requests) {
            if (request.getTicketType() == type) {
                total += request.getNoOfTickets();
            }
        }

        return total;
    }
}
