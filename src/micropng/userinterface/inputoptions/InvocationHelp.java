package micropng.userinterface.inputoptions;

import java.util.ArrayList;

import micropng.commonlib.Status;

public class InvocationHelp implements Parameter {
    private static final String longHelp = "Eine Übersicht mit knappen Erklärungen der einzelnen Parameter, die das Verhalten des Programms beim Start beeinflussen, wird angezeigt.";
    private static final String longParameterName = "help";
    private static final String shortHelp = "kurze Hilfe zum Programmaufruf";
    private static final char shortParameterName = 'h';

    @Override
    public String getLongHelp() {
	return longHelp;
    }

    @Override
    public String getLongParameterName() {
	return longParameterName;
    }

    @Override
    public ParameterGroup getParentGroup() {
	return ParameterGroup.INVOCATION_LINE;
    }

    @Override
    public String getShortHelp() {
	return shortHelp;
    }

    @Override
    public char getShortParameterName() {
	return shortParameterName;
    }

    @Override
    public boolean takesArgument() {
	return false;
    }

    @Override
    public Status validateAndSet(ArrayList<String> values) {
	return Status.ok();
    }
}
