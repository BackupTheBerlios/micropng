package micropng.userinterface.invocationline;

import java.io.File;
import java.util.ArrayList;

import micropng.commonlib.Status;
import micropng.userinterface.inputoptions.Path;

public class PathParser implements ValueParser {
    private final Path value;

    public PathParser(Path value) {
	this.value = value;
    }

    @Override
    public Status parseValue(ArrayList<String> input) {
	File resultingValue = null;
	for (String currentString : input) {
	    final File newResultingValue = new File(currentString).getAbsoluteFile();

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
