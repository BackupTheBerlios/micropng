package micropng.userinterface.invocationline;

import java.util.ArrayList;

import micropng.commonlib.Status;
import micropng.userinterface.inputoptions.OneOfMany;

public class OneOfManyParser implements ValueParser {
    private final OneOfMany value;

    public OneOfManyParser(OneOfMany value) {
	this.value = value;
    }

    @Override
    public Status parseValue(ArrayList<String> input) {
	String resultingValue = null;

	for (String currentString : input) {
	    final String newResultingValue = currentString.toLowerCase();

	    if (resultingValue == null) {
		resultingValue = newResultingValue;
	    } else if (!resultingValue.equals(newResultingValue)) {
		return Status.error("Wert " + currentString + " widerspricht vorangegangenem Wert");
	    }

	}
	return value.trySetting(resultingValue);
    }
}
