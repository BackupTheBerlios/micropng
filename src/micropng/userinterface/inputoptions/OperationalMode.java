package micropng.userinterface.inputoptions;

public class OperationalMode implements ParameterDescription {
    private static final String[] possibleValues = new String[] {"info", "action"};
    private static final String longHelp = "Bestimmt den Ausführungsmodus: Info zeigt Daten zu einer Datei, ohne etwas zu verändern oder zu erzeugen. Action erzeugt tatsächlich eine neue Datei.";
    private static final String longParameterName = "mode";
    private static final String shortHelp = "Ausführungsmodus";
    private static final char shortParameterName = 'm';
    private static final OneOfMany defaultValue;
    static {
	defaultValue = new OneOfMany(possibleValues);
	defaultValue.trySetting(possibleValues[1]);
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
