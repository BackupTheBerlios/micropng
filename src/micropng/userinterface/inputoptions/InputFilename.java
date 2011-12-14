package micropng.userinterface.inputoptions;

public class InputFilename implements ParameterDescription {
    private static final String longHelp = "Der Pfad zu einer Eingabedatei. Jede Art von Pfad, der von der JVM verarbeitet werden kann, ist erlaubt.";
    private static final String longParameterName = "input-file";
    private static final String shortHelp = "Pfad der Eingabedatei";
    private static final char shortParameterName = 'i';

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
	Path value = new Path();
	Parameter res = new Parameter(new InputFilename(), value);
	return res;
    }
}
