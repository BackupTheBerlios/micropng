package micropng.userinterface.inputoptions;

public class UserInterface implements ParameterDescription {
    private static final String[] possibleValues = new String[] { "none" };
    private static final String longHelp = "Legt eine interaktive Benutzeroberfläche fest. Mit " + possibleValues[0] + " wird keine gestartet, sondern das Programm direkt mit der Konfiguration der Kommandozeile ausgeführt.";
    private static final String longParameterName = "user-interface";
    private static final String shortHelp = "Benutzeroberfläche";
    private static final char shortParameterName = 'u';
    private static final OneOfMany defaultValue;
    static {
	defaultValue = new OneOfMany(possibleValues);
	defaultValue.trySetting(possibleValues[0]);
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
