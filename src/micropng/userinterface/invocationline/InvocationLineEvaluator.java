package micropng.userinterface.invocationline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import micropng.commonlib.Status;
import micropng.commonlib.Status.StatusType;
import micropng.userinterface.DuplicateParameterAssignment;
import micropng.userinterface.OutputHandler;
import micropng.userinterface.inputoptions.IntegerValue;
import micropng.userinterface.inputoptions.Parameter;
import micropng.userinterface.inputoptions.ParameterDescription;
import micropng.userinterface.inputoptions.ParameterTree;
import micropng.userinterface.inputoptions.ParameterTreeDescription;
import micropng.userinterface.inputoptions.Path;
import micropng.userinterface.inputoptions.ValueType;
import micropng.userinterface.inputoptions.YesNoSwitch;

public class InvocationLineEvaluator implements OutputHandler {
    private ParameterTree tree;
    private ArrayList<Parameter> parametersList;
    private HashMap<String, Parameter> longParametersTable;
    private HashMap<Character, Parameter> shortParametersTable;
    private HashMap<Parameter, ArrayList<String>> parameterLiterals;
    private HashMap<Parameter, ValueParser> parserTable;

    public InvocationLineEvaluator() {
	tree = ParameterTreeDescription.DEFAULT.instantiate();
	parametersList = tree.getRootNode().getAllDependingParameters();
	longParametersTable = new HashMap<String, Parameter>();
	shortParametersTable = new HashMap<Character, Parameter>();
	parameterLiterals = new HashMap<Parameter, ArrayList<String>>();
	parserTable = new HashMap<Parameter, ValueParser>();

	for (Parameter parameter : parametersList) {
	    ParameterDescription definition = parameter.getDescription();
	    String keyLongParameter = definition.getLongParameterName();
	    char keyShortParameter = definition.getShortParameterName();
	    ValueType valueType = parameter.getValueType();
	    ValueParser valueParser;
	    switch (valueType) {
	    case INTEGER_VALUE:
		valueParser = new IntegerValueParser((IntegerValue) parameter.getValue());
		break;
	    case PATH:
		valueParser = new PathParser((Path) parameter.getValue());
		break;
	    case YES_NO_SWITCH:
		valueParser = new YesNoSwitchParser((YesNoSwitch) parameter.getValue());
		break;
	    default:
		throw new MissingParserException();
	    }

	    parserTable.put(parameter, valueParser);

	    if (longParametersTable.containsKey(keyLongParameter)) {
		throw new DuplicateParameterAssignment();
	    }
	    if (shortParametersTable.containsKey(keyShortParameter)) {
		throw new DuplicateParameterAssignment();
	    }

	    longParametersTable.put(keyLongParameter, parameter);
	    shortParametersTable.put(keyShortParameter, parameter);
	}
    }

    private void readParametersFromArgs(String[] args) {
	int pos = 0;
	char shortParameterName;
	String longParameterName;
	Parameter parameter = null;

	while (pos < args.length) {
	    String currentString = args[pos];
	    ArrayList<String> values;

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

	    if (pos >= args.length) {
		error("fehlender Wert für Parameter " + currentString);
	    }

	    currentString = args[pos];

	    values = parameterLiterals.get(parameter);
	    if (values == null) {
		values = new ArrayList<String>();
		parameterLiterals.put(parameter, values);
	    }
	    values.add(currentString);

	    pos++;
	}
    }

    private void transformValues() {
	for (Map.Entry<Parameter, ArrayList<String>> entry : parameterLiterals.entrySet()) {
	    Parameter parameter = entry.getKey();
	    ValueParser parser = parserTable.get(parameter);
	    Status status = parser.parseValue(entry.getValue());

	    if (status.getStatusType() == StatusType.ERROR) {
		error(status.message());
	    }
	}
    }

    public void evaluate(String[] args) {
	readParametersFromArgs(args);
	transformValues();
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
