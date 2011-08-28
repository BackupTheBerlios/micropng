package micropng.userinterface.cli;

import micropng.commonlib.Status;

public class CLIStringParameter implements CLIParameter {

    private String value;

    @Override
    public Status inputArgument(String arg) {
	String newValue = arg;
	if (value == null || value.equals(newValue)) {
	    value = newValue;
	    return Status.ok();
	} else {
	    return Status.error("Argument mehrdeutig: " + value + " und " + newValue);
	}
    }
}
