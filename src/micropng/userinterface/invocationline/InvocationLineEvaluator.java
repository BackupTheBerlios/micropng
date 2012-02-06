package micropng.userinterface.invocationline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import micropng.commonlib.Status;
import micropng.commonlib.Status.StatusType;
import micropng.userinterface.OutputHandler;
import micropng.userinterface.UserConfiguration;
import micropng.userinterface.inputoptions.IntegerValue;
import micropng.userinterface.inputoptions.OneOfMany;
import micropng.userinterface.inputoptions.Parameter;
import micropng.userinterface.inputoptions.ParameterGroup;
import micropng.userinterface.inputoptions.Path;
import micropng.userinterface.inputoptions.ValueType;
import micropng.userinterface.inputoptions.YesNoSwitch;

public class InvocationLineEvaluator implements OutputHandler {
    private class ParserTable {
	private HashMap<Parameter, ValueParser> parserTable;

	public ParserTable() {
	    parserTable = new HashMap<Parameter, ValueParser>();
	}

	public void registerParameter(Parameter parameter) {
	    ValueType valueType = parameter.getValueType();
	    ValueParser valueParser;
	    switch (valueType) {
	    case INTEGER_VALUE:
		valueParser = new IntegerValueParser((IntegerValue) parameter.getValue());
		break;
	    case ONE_OF_MANY:
		valueParser = new OneOfManyParser((OneOfMany) parameter.getValue());
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
	}

	public ValueParser get(Parameter parameter) {
	    return parserTable.get(parameter);
	}
    }

    private ParameterGroup coreGroup;
    private UserConfiguration userConfiguration;
    private ParameterGroup invocationLineGroup = InvocationLineGroup.INVOCATION.getGroup();
    private HashMap<String, Parameter> longNameLookUpTable = new HashMap<String, Parameter>();
    private HashMap<Character, Parameter> shortNameLookUpTable = new HashMap<Character, Parameter>();
    private ParserTable parserTable = new ParserTable();
    private HashMap<Parameter, ArrayList<String>> parameterValuesLiterals = new HashMap<Parameter, ArrayList<String>>();

    public InvocationLineEvaluator(ParameterGroup coreGroup) {
	this.coreGroup = coreGroup;
	userConfiguration = new UserConfiguration(coreGroup);

	for (Parameter parameter : userConfiguration) {
	    parserTable.registerParameter(parameter);
	    longNameLookUpTable.put(parameter.getLongParameterName(), parameter);
	    shortNameLookUpTable.put(parameter.getShortParameterName(), parameter);
	}
	for (Parameter parameter : invocationLineGroup) {
	    parserTable.registerParameter(parameter);
	    longNameLookUpTable.put(parameter.getLongParameterName(), parameter);
	    shortNameLookUpTable.put(parameter.getShortParameterName(), parameter);
	}
    }

    private void addLiteralToParam(Parameter parameter, String literal) {
	ArrayList<String> values = parameterValuesLiterals.get(parameter);
	if (values == null) {
	    values = new ArrayList<String>();
	    parameterValuesLiterals.put(parameter, values);
	}
	values.add(literal);
    }

    private Parameter lookUpShortName(char shortName) {
	Parameter res = shortNameLookUpTable.get(shortName);
	if (res == null) {
	    wrongUsage("unbekannter Parameter \"" + shortName + "\"");
	}
	return res;
    }

    private Parameter lookUpLongName(String longName) {
	Parameter res = longNameLookUpTable.get(longName);
	if (res == null) {
	    wrongUsage("unbekannter Parameter \"" + longName + "\"");
	}
	return res;
    }

    private void wrongUsage(String msg) {
	error(msg + "\nHinweis: --help erklärt den Aufruf des Programms.");
    }

    private void evaluateBooleanDefault(Parameter parameter) {
	if (parameter.getValueType() == ValueType.YES_NO_SWITCH) {
	    addLiteralToParam(parameter, "yes");
	} else {
	    wrongUsage("Parameter -" + parameter.getShortParameterName() + ", --"
		    + parameter.getLongParameterName() + " benötigt einen Wert");
	}
    }

    private void readParametersFromArgs(String[] args) {
	int pos = 0;
	Parameter parameter = null;

	while (pos < args.length) {
	    String currentString = args[pos];
	    String[] splitStrings = currentString.split("=", 2);
	    String name = splitStrings[0];

	    if (name.length() < 2 || name.charAt(0) != '-') {
		wrongUsage("unverständliches Argument " + pos + ": \"" + currentString + "\"");
	    } else if (name.charAt(1) != '-') {
		int index = 1;

		while (index < name.length() - 1) {
		    parameter = lookUpShortName(name.charAt(index));
		    evaluateBooleanDefault(parameter);
		    index++;
		}

		parameter = lookUpShortName(name.charAt(index));
	    } else {
		parameter = lookUpLongName(name.substring(2));
	    }

	    if (splitStrings.length == 1) {
		evaluateBooleanDefault(parameter);
	    } else {
		addLiteralToParam(parameter, splitStrings[1]);
	    }

	    pos++;
	}
    }

    private void transformValues() {
	for (Map.Entry<Parameter, ArrayList<String>> entry : parameterValuesLiterals.entrySet()) {
	    Parameter parameter = entry.getKey();
	    ValueParser parser = parserTable.get(parameter);
	    Status status = parser.parseValue(entry.getValue());

	    if (status.getStatusType() == StatusType.ERROR) {
		error(status.message());
	    }
	}
    }

    private void printHelpGroup(ParameterGroup group, boolean longHelp) {
	info("\n" + group.getName() + "\n");
	for (Parameter parameter : group.getParameters()) {
	    printHelpParameter(parameter, longHelp);
	}
	for (ParameterGroup subGroup : group.getSubGroups()) {
	    printHelpGroup(subGroup, longHelp);
	}
    }

    private void printHelpParameter(Parameter parameter, boolean longHelp) {
	int consoleLineWidth = 80;
	int parameterNamesWidth = 24;
	int helpTextMaximumWidth = consoleLineWidth - parameterNamesWidth - 1;
	int paddingLength;
	StringBuffer paddingSpace = new StringBuffer();
	String shortNamePrefix = " -";
	StringBuffer shortNameString = new StringBuffer();
	String delimiter = ", ";
	String longNamePrefix = " --";
	String defaultValuePrefix = " [";
	String defaultValue = parameter.defaultValue().toString();
	String defaultValueSuffix = "]";
	StringBuffer longNameString = new StringBuffer();
	StringBuffer fullNameString = new StringBuffer();
	String spaceAsWidthAsParameterNames = "                        ";
	String helpText = longHelp ? parameter.getLongHelp() : parameter.getShortHelp();
	StringBuffer fullTextString = new StringBuffer(helpText);
	String[] splitText;
	int splitTextPosition = 0;
	StringBuffer nextChunk;

//	if (defaultValue.length() != 0) {
	    fullTextString.append(defaultValuePrefix);
	    fullTextString.append(defaultValue);
	    fullTextString.append(defaultValueSuffix);
//	}
	splitText = fullTextString.toString().split(" ");

	shortNameString.append(shortNamePrefix);
	shortNameString.append(parameter.getShortParameterName());

	longNameString.append(longNamePrefix);
	longNameString.append(parameter.getLongParameterName());

	fullNameString.append(shortNameString);
	fullNameString.append(delimiter);
	fullNameString.append(longNameString);

	paddingLength = parameterNamesWidth - fullNameString.length();

	info(fullNameString.toString());

	for (int i = 0; i < paddingLength; i++) {
	    paddingSpace.append(' ');
	}
	info(paddingSpace.toString());

	do {
	    nextChunk = new StringBuffer();
	    nextChunk.append(splitText[splitTextPosition]);
	    splitTextPosition++;

	    while ((splitTextPosition < splitText.length)
		    && (nextChunk.length() + splitText[splitTextPosition].length() < helpTextMaximumWidth)) {
		nextChunk.append(' ');
		nextChunk.append(splitText[splitTextPosition]);
		splitTextPosition++;
	    }

	    nextChunk.append('\n');
	    info(nextChunk.toString());
	    if (splitTextPosition < splitText.length) {
		info(spaceAsWidthAsParameterNames);
	    }
	} while (splitTextPosition < splitText.length);
    }

    private void usageIfRequested() {
	Parameter longHelp = InvocationLineGroup.getLongHelpInstance();
	Parameter shortHelp = InvocationLineGroup.getShortHelpInstance();
	boolean longHelpSet = ((YesNoSwitch) longHelp.getValue()).getValue();
	boolean shortHelpSet = ((YesNoSwitch) shortHelp.getValue()).getValue();

	if (longHelpSet) {
	    info("micropng, 2009 bis 2011, von Martin Walch, http://micropng.berlios.de/\n");
	    info("Benutzung: <java-Aufruf> [<Parameter>=<Wert>] ...\n");
	    printHelpGroup(invocationLineGroup, true);
	    printHelpGroup(coreGroup, true);
	    info("\nPositive Wahrheitswerte von einfachen Schaltern können weggelassen werden.\n");
	    info("Beispiel: -h=y ist gleichbedeutend mit -h.\n\n");
	    info("Außerdem können Parameter in ihrer Kurzform aggregiert werden, sofern alle\n");
	    info("bis auf den letzten einfache Schalter sind, die auf wahr gesetzt werden.\n");
	    info("Beispiel: -c=y -s=y -i=input.png ist äquivalent zu -csi=input.png\n");
	    System.exit(0);
	}
	if (shortHelpSet) {
	    info("Benutzung: <java-Aufruf> [<Parameter>=<Wert>] ...\n");
	    printHelpGroup(invocationLineGroup, false);
	    printHelpGroup(coreGroup, false);
	    System.exit(0);
	}
    }

    public UserConfiguration evaluate(String[] args) {
	readParametersFromArgs(args);
	transformValues();
	usageIfRequested();
	return userConfiguration;
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
