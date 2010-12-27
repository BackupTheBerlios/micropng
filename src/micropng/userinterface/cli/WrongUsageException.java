package micropng.userinterface.cli;

@SuppressWarnings("serial")
public class WrongUsageException extends Exception {

    public WrongUsageException(String message) {
	super(message);
    }
}
