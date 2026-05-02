# Cinema Tickets Service

## Overview

This project implements a cinema ticket purchasing service that validates ticket requests, calculates payment, and reserves seats using the provided third-party services.

## Structure

```text
TicketServiceImpl         -> Main service / purchase flow
TicketPurchaseValidator   -> Business rule validation
TicketCalculator          -> Payment + seat calculations
TicketPaymentService      -> External payment service
SeatReservationService    -> External seat booking service
```

## Business Rules

The service follows these business rules:

- There are 3 types of tickets:
  - `INFANT`
  - `CHILD`
  - `ADULT`
- The ticket purchaser declares how many tickets they want to buy and the type of each ticket.
- Multiple tickets can be purchased at the same time.
- A maximum of 25 tickets can be purchased in a single transaction.
- Infant tickets are free.
- Infant tickets are not allocated seats because infants sit on an adult's lap.
- Child and infant tickets cannot be purchased unless at least one adult ticket is also purchased.
- The `TicketTypeRequest` object is immutable.

## Ticket Prices

| Ticket Type | Price |
|------------|-------|
| `INFANT`   | £0    |
| `CHILD`    | £15   |
| `ADULT`    | £25   |

## Seat Allocation Rules

| Ticket Type | Seat Allocated? |
|------------|-----------------|
| `INFANT`   | No              |
| `CHILD`    | Yes             |
| `ADULT`    | Yes             |

## Implementation Details

The main ticket purchase flow validates the request before taking payment or reserving seats.

The implementation ensures that:

- Invalid account IDs are rejected.
- Invalid ticket requests are rejected.
- The total number of tickets does not exceed 25.
- Child tickets cannot be bought without an adult ticket.
- Infant tickets cannot be bought without an adult ticket.
- Infants are excluded from seat reservation.
- Payment is only taken for adult and child tickets.
- Seats are only reserved for adult and child tickets.
