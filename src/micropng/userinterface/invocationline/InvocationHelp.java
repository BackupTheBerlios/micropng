package micropng.userinterface.invocationline;

import java.util.ArrayList;

import micropng.commonlib.Status;
import micropng.userinterface.inputoptions.ParameterDescription;
import micropng.userinterface.inputoptions.ParameterGroup;

public class InvocationHelp implements ParameterDescription {
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
	//return ParameterGroup.INVOCATION_LINE;
	return null;
    }

    @Override
    public String getShortHelp() {
	return shortHelp;
    }

    @Override
    public char getShortParameterName() {
	return shortParameterName;
    }

    public Status validateAndSet(ArrayList<String> values) {
	return Status.ok();
    }
}
