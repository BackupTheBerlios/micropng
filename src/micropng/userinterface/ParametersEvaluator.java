package micropng.userinterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import micropng.commonlib.Status;
import micropng.commonlib.Status.StatusType;
import micropng.userinterface.inputoptions.ParameterDefinition;
import micropng.userinterface.inputoptions.Parameter;

public class ParametersEvaluator {
    private HashMap<String, ParameterDefinition> longParametersTable;
    private HashMap<Character, ParameterDefinition> shortParametersTable;
    private HashMap<ParameterDefinition, ArrayList<String>> parameterValues;

    public ParametersEvaluator() {
	longParametersTable = new HashMap<String, ParameterDefinition>();
	shortParametersTable = new HashMap<Character, ParameterDefinition>();
	parameterValues = new HashMap<ParameterDefinition, ArrayList<String>>();

	for (Parameter p : Parameter.values()) {
	    ParameterDefinition definition = p.getParameterObject();
	    String keyLongParameter = definition.getLongParameterName();
	    char keyShortParameter = definition.getShortParameterName();

	    if (longParametersTable.containsKey(keyLongParameter)) {
		throw new DuplicateParameterAssignment();
	    }
	    if (shortParametersTable.containsKey(keyShortParameter)) {
		throw new DuplicateParameterAssignment();
	    }

	    longParametersTable.put(keyLongParameter, definition);
	    shortParametersTable.put(keyShortParameter, definition);
	}
    }

    private void readParametersFromArgs(String[] args) {
	int pos = 0;
	char shortParameterName;
	String longParameterName;
	ParameterDefinition definition = null;

	while (pos < args.length) {
	    String currentString = args[pos];
	    if (currentString.length() < 2) {
		error("unverständliches Argument " + pos + ": \"" + currentString + "\"");
	    } else if (currentString.length() == 2) {
		if (currentString.charAt(0) != '-' || currentString.charAt(1) == '-') {
		    error("unverständliches Argument " + pos + ": \"" + currentString + "\"");
		}
		shortParameterName = currentString.charAt(1);
		definition = shortParametersTable.get(shortParameterName);
	    } else {
		if (currentString.charAt(0) != '-' || currentString.charAt(1) != '-') {
		    error("Syntax bei Argument " + pos + ": \"" + currentString + "\"");
		}
		longParameterName = currentString.substring(2);
		definition = longParametersTable.get(longParameterName);
	    }
	    if (pos >= args.length) {
		error("fehlender Wert für Parameter " + currentString);
	    }

	    if (definition == null) {
		error("unbekannter Parameter \"" + currentString + "\"");
	    }

	    pos++;
	    currentString = args[pos];

	    ArrayList<String> values = parameterValues.get(definition);
	    if (values == null) {
		values = new ArrayList<String>();
		parameterValues.put(definition, values);
	    }
	    values.add(currentString);

	    pos++;
	}
    }

    private void validateParameters() {
	for (Entry<ParameterDefinition, ArrayList<String>> map : parameterValues.entrySet()) {
	    Status status = map.getKey().validateAndSet(map.getValue());
	    if (status.getStatusType() == StatusType.ERROR) {
		error(status.message());
	    }
	}
    }

    public void evaluate(String[] args) {
	readParametersFromArgs(args);
	validateParameters();
    }

    public void error(String message) {
	System.err.println("Fehler: " + message);
	System.exit(-1);
    }
}
