package uk.gov.dwp.uc.pairtest.exception;

public class InvalidAccountIDException extends RuntimeException {
    public InvalidAccountIDException(String message) {
        super(message);
    }
}
