package micropng.userinterface.invocationline;

import java.util.ArrayList;

import micropng.commonlib.Status;
import micropng.userinterface.inputoptions.IntegerValue;

public class IntegerValueParser implements ValueParser {
    private final IntegerValue value;

    public IntegerValueParser(IntegerValue value) {
	this.value = value;
    }

    @Override
    public Status parseValue(ArrayList<String> input) {
	Long resultingValue = null;
	for (String currentString : input) {
	    final Long newResultingValue = Long.valueOf(currentString);

	    if ((resultingValue != null) && (!resultingValue.equals(newResultingValue))) {
		return Status.error("Wert " + currentString
			+ " kann nicht mit vorangegangenem Wert vereinigt werden");
	    }
	    resultingValue = newResultingValue;
	}

	return value.trySetting(resultingValue);
    }
}
