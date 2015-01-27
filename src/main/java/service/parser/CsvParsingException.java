package service.parser;

/**
 * Exception that occurs when csv parsing failed.
 *
 * @author vladimir.tikhomirov
 */
public class CsvParsingException extends Exception {

    private final String message;

    public CsvParsingException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
