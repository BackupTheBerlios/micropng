package micropng.userinterface.invocationline;

import java.util.ArrayList;

import micropng.commonlib.Status;
import micropng.userinterface.inputoptions.OneOfMany;

public class OneOfManyParser implements ValueParser {
    private OneOfMany value;

    public OneOfManyParser(OneOfMany value) {
	this.value = value;
    }

    @Override
    public Status parseValue(ArrayList<String> input) {
	String resultingValue = null;

	for (String currentString : input) {
	    String newResultingValue = currentString.toLowerCase();

	    if ((resultingValue != null) && (!resultingValue.equals(newResultingValue))) {
		return Status.error("Wert " + currentString
			+ " kann nicht mit vorangegangenem Wert vereinigt werden");
	    } else {
		resultingValue = newResultingValue;
	    }
	}
	return value.trySetting(resultingValue);
    }
}
