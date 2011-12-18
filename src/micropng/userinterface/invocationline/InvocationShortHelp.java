package micropng.userinterface.invocationline;

import micropng.userinterface.inputoptions.ParameterDescription;
import micropng.userinterface.inputoptions.ParameterValue;
import micropng.userinterface.inputoptions.YesNoSwitch;

public class InvocationShortHelp implements ParameterDescription {
    private static final String longHelp = "Eine Übersicht mit knappen Erklärungen der einzelnen Parameter, die das Verhalten des Programms beim Start beeinflussen, wird angezeigt.";
    private static final String longParameterName = "help";
    private static final String shortHelp = "kurze Hilfe zum Programmaufruf";
    private static final char shortParameterName = 'h';
    private static final YesNoSwitch defaultValue = new YesNoSwitch();
    static {
	defaultValue.trySetting(false);
    }

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
    public ParameterValue<?> defaultValue() {
	return defaultValue.clone();
    }
}
