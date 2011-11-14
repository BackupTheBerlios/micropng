package micropng.userinterface.inputoptions;

import java.util.ArrayList;

import micropng.commonlib.Status;

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
    public ParameterGroup getParentGroup() {
	return ParameterGroup.CORE;
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
    public boolean takesArgument() {
	return true;
    }

    @Override
    public Status validateAndSet(ArrayList<String> values) {
	String path = values.get(0);
	for (int i = 1; i < values.size(); i++) {
	    if (values.get(i).compareTo(path) != 0) {
		return Status.error("Mehrere verschiedene Pfade als Eingabe angegeben.");
	    }
	}
	return Status.ok();
    }

}
