package NS.pgmg.exception;

public class TokenNullException extends RuntimeException {
    public TokenNullException() {
        super();
    }

    public TokenNullException(String message) {
        super(message);
    }

    public TokenNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenNullException(Throwable cause) {
        super(cause);
    }

    protected TokenNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
