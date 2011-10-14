package micropng.userinterface.cli;

import micropng.userinterface.UserConfiguration;

public class CommandLineParser {

    private String[] parameters;
    private int pos;
    private UserConfiguration res;

    public CommandLineParser(String[] parameters) {
	this.parameters = parameters;
	pos = 0;
	res = null;
    }

    private void parseDrop() {
	pos++;
    }

    private void parseFilename() {
	res.setPath(parameters[pos]);
	pos++;
    }

    private void parseShortOpt() {
	pos++;
    }

    private void parseLongOpt() {
	pos++;
    }

    public UserConfiguration parse() {

	while (pos < parameters.length) {
	    String nextString = parameters[pos];

	    switch (nextString.length()) {
	    case 0:
		parseDrop();
		break;
	    case 1:
		parseFilename();
		break;
	    default:
		if (nextString.charAt(0) == '-') {
		    if (nextString.charAt(1) != '-') {
			parseShortOpt();
		    } else {
			if (nextString.length() > 2) {
			    parseLongOpt();
			}
		    }
		} else {
		    parseFilename();
		}
		break;
	    }
	}
	return res;
    }
}
