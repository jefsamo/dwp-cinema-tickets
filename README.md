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
