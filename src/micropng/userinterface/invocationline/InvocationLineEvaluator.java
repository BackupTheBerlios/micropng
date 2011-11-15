package micropng.userinterface.invocationline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import micropng.commonlib.Status;
import micropng.commonlib.Status.StatusType;
import micropng.userinterface.DuplicateParameterAssignment;
import micropng.userinterface.OutputHandler;
import micropng.userinterface.inputoptions.ParameterDescription;
import micropng.userinterface.inputoptions.ParameterType;

public class InvocationLineEvaluator implements OutputHandler {
    private HashMap<String, ParameterDescription> longParametersTable;
    private HashMap<Character, ParameterDescription> shortParametersTable;
    private HashMap<ParameterDescription, ArrayList<String>> parameterValues;

    public InvocationLineEvaluator() {
	longParametersTable = new HashMap<String, ParameterDescription>();
	shortParametersTable = new HashMap<Character, ParameterDescription>();
	parameterValues = new HashMap<ParameterDescription, ArrayList<String>>();

	for (ParameterType p : ParameterType.values()) {
	    ParameterDescription definition = p.getParameterObject();
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
	ParameterDescription parameter = null;

	while (pos < args.length) {
	    String currentString = args[pos];
	    if (currentString.length() < 2) {
		error("unverständliches Argument " + pos + ": \"" + currentString + "\"");
	    } else if (currentString.length() == 2) {
		if (currentString.charAt(0) != '-' || currentString.charAt(1) == '-') {
		    error("unverständliches Argument " + pos + ": \"" + currentString + "\"");
		}
		shortParameterName = currentString.charAt(1);
		parameter = shortParametersTable.get(shortParameterName);
	    } else {
		if (currentString.charAt(0) != '-' || currentString.charAt(1) != '-') {
		    error("Syntax bei Argument " + pos + ": \"" + currentString + "\"");
		}
		longParameterName = currentString.substring(2);
		parameter = longParametersTable.get(longParameterName);
	    }

	    pos++;

	    if (parameter == null) {
		error("unbekannter Parameter \"" + currentString + "\"");
	    }

//	    if (parameter.takesArgument()) {
//		if (pos >= args.length) {
//		    error("fehlender Wert für Parameter " + currentString);
//		}
//
//		currentString = args[pos];
//
//		ArrayList<String> values = parameterValues.get(parameter);
//		if (values == null) {
//		    values = new ArrayList<String>();
//		    parameterValues.put(parameter, values);
//		}
//		values.add(currentString);
//
//		pos++;
//	    }
	}
    }

//    private void validateParameters() {
//	for (Entry<ParameterDescription, ArrayList<String>> map : parameterValues.entrySet()) {
//	    Status status = map.getKey().validateAndSet(map.getValue());
//	    if (status.getStatusType() == StatusType.ERROR) {
//		error(status.message());
//	    }
//	}
//    }

    public void evaluate(String[] args) {
	readParametersFromArgs(args);
//	validateParameters();
    }

    @Override
    public void error(String message) {
	System.err.println("Fehler: " + message);
	System.exit(-1);
    }

    @Override
    public void debug(String message) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void info(String message) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void warn(String message) {
	// TODO Auto-generated method stub
	
    }
}
