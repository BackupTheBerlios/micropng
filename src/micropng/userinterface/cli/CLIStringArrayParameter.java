package micropng.userinterface.cli;

import java.util.Arrays;
import java.util.TreeSet;

import micropng.commonlib.Status;

public class CLIStringArrayParameter implements CLIParameter {

    private static final String delimiter = ",";
    private TreeSet<String> value;

    public CLIStringArrayParameter() {
	value = new TreeSet<String>();
    }

    @Override
    public Status inputArgument(String argument) {
	String[] additionalValue = argument.split(delimiter);
	value.addAll(Arrays.asList(additionalValue));
	return Status.ok();
    }
}
