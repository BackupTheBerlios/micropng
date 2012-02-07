package micropng.userinterface.invocationline;

import java.util.ArrayList;
import java.util.HashMap;

import micropng.commonlib.Status;
import micropng.userinterface.inputoptions.YesNoSwitch;

public class YesNoSwitchParser implements ValueParser {
    private final static String[] trueLiterals = new String[] { "yes", "y", "1", "true" };
    private final static String[] falseLiterals = new String[] { "no", "n", "0", "false" };
    private final static HashMap<String, Boolean> literalsInterpretations;

    static {
	literalsInterpretations = new HashMap<String, Boolean>();
	for (String literal : trueLiterals) {
	    literalsInterpretations.put(literal, Boolean.TRUE);
	}
	for (String literal : falseLiterals) {
	    literalsInterpretations.put(literal, Boolean.FALSE);
	}
    }

    private final YesNoSwitch value;

    public YesNoSwitchParser(YesNoSwitch value) {
	this.value = value;
    }

    @Override
    public Status parseValue(ArrayList<String> input) {
	Boolean resultingValue = null;

	for (String currentString : input) {
	    final String lowerCaseString = currentString.toLowerCase();
	    final Boolean newResultingValue;

	    if (!literalsInterpretations.containsKey(lowerCaseString)) {
		return Status.error("Wert " + currentString + " nicht verstanden");
	    }

	    newResultingValue = literalsInterpretations.get(lowerCaseString);

	    if (resultingValue == null) {
		resultingValue = newResultingValue;
	    } else if (!resultingValue.equals(newResultingValue)) {
		return Status.error("Wert " + currentString + " widerspricht vorangegangenem Wert");
	    }
	}

	return value.trySetting(resultingValue);
    }
}
