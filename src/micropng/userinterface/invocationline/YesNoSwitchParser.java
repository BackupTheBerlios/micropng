package micropng.userinterface.invocationline;

import java.util.ArrayList;
import java.util.HashMap;

import micropng.commonlib.Status;
import micropng.userinterface.inputoptions.YesNoSwitch;

public class YesNoSwitchParser implements ValueParser {
    private static String[] trueLiterals = new String[] { "yes", "y", "1", "true" };
    private static String[] falseLiterals = new String[] { "no", "n", "0", "false" };
    private static HashMap<String, Boolean> literalsInterpretations;

    static {
	HashMap<String, Boolean> literalsInterpretations = new HashMap<String, Boolean>();
	for (String literal : trueLiterals) {
	    literalsInterpretations.put(literal, Boolean.TRUE);
	}
	for (String literal : falseLiterals) {
	    literalsInterpretations.put(literal, Boolean.FALSE);
	}
    }

    private YesNoSwitch value;

    public YesNoSwitchParser(YesNoSwitch value) {
	this.value = value;
    }

    @Override
    public Status parseValue(ArrayList<String> input) {
	Boolean resultingValue = null;

	for (String currentString : input) {
	    String lowerCaseString = currentString.toLowerCase();
	    if (!literalsInterpretations.containsKey(lowerCaseString)) {
		return Status.error("Wert " + currentString + " nicht verstanden");
	    } else {
		Boolean newResultingValue = literalsInterpretations.get(lowerCaseString);
		if ((resultingValue != null) && (!resultingValue.equals(newResultingValue))) {
		    return Status.error("Wert " + currentString
			    + " widerspricht vorangegangenem Wert");
		} else {
		    resultingValue = newResultingValue;
		}
	    }
	}

	return value.trySetting(resultingValue);
    }
}
