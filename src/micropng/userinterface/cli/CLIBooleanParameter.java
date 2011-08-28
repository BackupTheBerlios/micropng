package micropng.userinterface.cli;

import micropng.commonlib.Status;

public class CLIBooleanParameter implements CLIParameter {

    private static final String[] trueNames = { "yes", "1", "true" };
    private static final String[] falseNames = { "no", "0", "false" };

    private Boolean value;

    private int parse(String argument) {
	for (String s : trueNames) {
	    if (argument.equalsIgnoreCase(s)) {
		return 1;
	    }
	}
	for (String s : falseNames) {
	    if (argument.equalsIgnoreCase(s)) {
		return 0;
	    }
	}
	return -1;
    }

    @Override
    public Status inputArgument(String argument) {
	int newValue = parse(argument);
	boolean newBoolValue;

	if (newValue == -1) {
	    return Status.error("nicht als boolescher Wert erkannt „" + argument + "“");
	}

	 newBoolValue = (newValue == 1);
	
	if (value != null) {
	    if (!value.equals(newBoolValue)) {
		return Status.error("Argument widersprüchlich");
	    }
	} else {
	    value = newBoolValue;
	}
	return Status.ok();
    }
}
