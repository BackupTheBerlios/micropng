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
import micropng.userinterface.inputoptions.ParameterGroup;
import micropng.userinterface.inputoptions.ParameterGroupDescription;
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
    private Parameter invocationShortHelpInstance;
    private Parameter invocationLongHelpInstance;

    public InvocationLineEvaluator() {
	tree = ParameterTreeDescription.DEFAULT.instantiate();
	invocationShortHelpInstance = new InvocationShortHelp().instantiate();
	invocationLongHelpInstance = new InvocationLongHelp().instantiate();
	parametersList = tree.getRootNode().getAllDependingParameters();
	parametersList.add(invocationShortHelpInstance);
	parametersList.add(invocationLongHelpInstance);
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

    private void printHelpGroup(ParameterGroup group, boolean longHelp) {
	ParameterGroupDescription groupDescription = group.getDescription();
	info("\n" + groupDescription.getGroupName() + "\n");
	for (Parameter parameter : group.getParameters()) {
	    printHelpParameter(parameter, longHelp);
	}
	for (ParameterGroup subGroup : group.getSubGroups()) {
	    printHelpGroup(subGroup, longHelp);
	}
    }

    private void printHelpParameter(Parameter parameter, boolean longHelp) {
	ParameterDescription description = parameter.getDescription();
	int parameterNamesWidth = 24;
	int consoleLineWidth = 80;
	int helpTextMaximumWidth = consoleLineWidth - parameterNamesWidth - 1;
	String spaceAsWidthAsParameterNames = "                        ";
	int longNameLength = description.getLongParameterName().length();
	int spaceLength = parameterNamesWidth - 9 - longNameLength;
	String helpText = longHelp ? description.getLongHelp() : description.getShortHelp();
	String[] splitText = helpText.split(" ");
	int splitTextPosition = 0;
	StringBuffer nextChunk = new StringBuffer();
	StringBuffer variableLengthSpace = new StringBuffer();

	info(" -" + description.getShortParameterName() + ", ");
	info(" --" + description.getLongParameterName() + ":");

	for (int i = 0; i < spaceLength; i++) {
	    variableLengthSpace.append(' ');
	}
	info(variableLengthSpace.toString());

	nextChunk.append(splitText[splitTextPosition]);
	splitTextPosition++;

	do {
	    nextChunk = new StringBuffer();
	    while ((splitTextPosition < splitText.length)
		    && (nextChunk.length() + splitText[splitTextPosition].length() < helpTextMaximumWidth)) {
		nextChunk.append(" ");
		nextChunk.append(splitText[splitTextPosition]);
		splitTextPosition++;
	    }

	    nextChunk.append("\n");
	    info(nextChunk.toString());
	    if (splitTextPosition < splitText.length) {
		info(spaceAsWidthAsParameterNames);
	    }
	} while (splitTextPosition < splitText.length);

    }

    private void usageIfRequested() {
	if (((YesNoSwitch) invocationShortHelpInstance.getValue()).getValue()) {
	    info("Kurzhilfe zum Aufruf von micropng\n\n");
	    info("Parameter, spezifisch für den Programmaufruf\n");
	    printHelpParameter(invocationShortHelpInstance, false);
	    printHelpParameter(invocationLongHelpInstance, false);
	    printHelpGroup(tree.getRootNode(), false);
	}
	if (((YesNoSwitch) invocationLongHelpInstance.getValue()).getValue()) {
	    info("ausführliche Hilfe zum Aufruf von micropng\n\n");
	    info("Parameter, spezifisch für den Programmaufruf\n");
	    printHelpParameter(invocationShortHelpInstance, true);
	    printHelpParameter(invocationLongHelpInstance, true);
	    printHelpGroup(tree.getRootNode(), true);
	}
    }

    public void evaluate(String[] args) {
	readParametersFromArgs(args);
	transformValues();
	usageIfRequested();
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
	System.out.print(message);
    }

    @Override
    public void warn(String message) {
	// TODO Auto-generated method stub

    }
}
