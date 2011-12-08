package micropng.userinterface.invocationline;

import micropng.userinterface.inputoptions.Parameter;
import micropng.userinterface.inputoptions.ParameterDescription;
import micropng.userinterface.inputoptions.YesNoSwitch;

public class InvocationShortHelp implements ParameterDescription {
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
    public String getShortHelp() {
	return shortHelp;
    }

    @Override
    public char getShortParameterName() {
	return shortParameterName;
    }

    @Override
    public Parameter instantiate() {
	YesNoSwitch value = new YesNoSwitch();
	Parameter res = new Parameter(this, value);
	value.trySetting(false);
	return res;
    }
}
