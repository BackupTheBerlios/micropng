package micropng.userinterface.inputoptions;

public class InputFilename implements ParameterDescription {
    private static final String longHelp = "Der Pfad zu einer Eingabedatei. Jede Art von Pfad, der von der JVM verarbeitet werden kann, ist erlaubt.";
    private static final String longParameterName = "input-file";
    private static final String shortHelp = "Pfad der Eingabedatei";
    private static final char shortParameterName = 'i';
    private static final Path defaultValue = new Path();
    static {
	defaultValue.trySetting(null);
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
