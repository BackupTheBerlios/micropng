package micropng.userinterface.inputoptions;

public class OutputFilename implements ParameterDescription {
    private static final String longHelp = "Der Pfad zu einer Ausgabedatei. Jede Art von Pfad, der von der JVM verarbeitet werden kann, ist erlaubt. Die Datei darf noch nicht existieren.";
    private static final String longParameterName = "output-file";
    private static final String shortHelp = "Pfad der Ausgabedatei";
    private static final char shortParameterName = 'o';
    private static final Path defaultValue = new Path();

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
