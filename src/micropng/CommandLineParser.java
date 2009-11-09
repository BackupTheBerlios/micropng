package micropng;

public class CommandLineParser {

    private int pos;
    private String[] parameters;
    private Configuration res;
    private boolean filenameHasBeenFound;

    public CommandLineParser(String[] parameters) {
	pos = 0;
	this.parameters = parameters;
	res = new Configuration();
	filenameHasBeenFound = false;
    }

    private void parseDrop() {
	pos++;
    }

    private void parseFilename() throws WrongUsageException {
	if (filenameHasBeenFound) {
	    throw new WrongUsageException("can not take more than one filename: \""
		    + parameters[pos] + "\"");
	}
	res.setFilename(parameters[pos]);
	filenameHasBeenFound = true;
	pos++;
    }

    private void parseShortOpt() {
	pos++;
    }

    private void parseLongOpt() {
	pos++;
    }

    public Configuration parse() throws WrongUsageException {

	while (pos < parameters.length) {
	    String nextString = parameters[pos];

	    switch (nextString.length()) {
	    case 0:
		parseDrop();
		break;
	    case 1:
		parseFilename();
		break;
	    case 2:
		if ((nextString.charAt(0) == '-') && (nextString.charAt(1) != '-')) {
		    parseShortOpt();
		} else {
		    parseFilename();
		}
		break;
	    default:
		if (nextString.charAt(0) == '-') {
		    if (nextString.charAt(1) == '-') {
			parseLongOpt();
		    } else {
			parseShortOpt();
		    }
		} else {
		    parseFilename();
		}
	    }
	}
	return res;
    }
}
