package micropng.userinterface.invocationline;

import micropng.userinterface.inputoptions.Parameter;
import micropng.userinterface.inputoptions.ParameterDescription;
import micropng.userinterface.inputoptions.YesNoSwitch;

public class InvocationLongHelp implements ParameterDescription {
    private static final String longHelp = "Eine Übersicht mit detaillierten Erklärungen der einzelnen Parameter, die das Verhalten des Programms beim Start beeinflussen, wird angezeigt.";
    private static final String longParameterName = "long-help";
    private static final String shortHelp = "ausführliche Hilfe zum Programmaufruf";
    private static final char shortParameterName = 'l';

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

    public static Parameter instance() {
	YesNoSwitch value = new YesNoSwitch();
	Parameter res = new Parameter(new InvocationLongHelp(), value);
	value.trySetting(false);
	return res;
    }
}
