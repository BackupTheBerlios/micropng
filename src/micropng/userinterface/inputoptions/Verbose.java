package micropng.userinterface.inputoptions;

public class Verbose implements ParameterDescription {
    private static final String longHelp = "deutlich ausführlichere Informationen zur Datei und zur Ausführung des Programms werden ausgegeben";
    private static final String longParameterName = "verbose";
    private static final String shortHelp = "ausführliche Ausgabe";
    private static final char shortParameterName = 'v';
    private static final YesNoSwitch defaultValue = new YesNoSwitch();
    static {
	defaultValue.trySetting(false);
    }

    @Override
    public ParameterValue<?> defaultValue() {
	return defaultValue.clone();
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
}
