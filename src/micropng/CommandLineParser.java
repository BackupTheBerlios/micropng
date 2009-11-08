package micropng;

public class CommandLineParser {

    boolean filenameFound;

    public CommandLineParser() {
	filenameFound = false;
    }

    private void parseFilename() {

    }

    public Configuration parse(String[] parameters) throws WrongUsageException {
	Configuration res = new Configuration();

	for (int i = 0; i < parameters.length; i++) {

	}

	return res;
    }
}
