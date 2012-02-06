package micropng.userinterface.inputoptions;

import java.util.ArrayList;
import java.util.Collections;

import micropng.commonlib.Status;

public class OneOfMany implements ParameterValue<String> {
    private final ArrayList<String> possibleValues;
    private String value;

    public OneOfMany(String[] possibleValues) {
	this.possibleValues = new ArrayList<String>(possibleValues.length);
	Collections.addAll(this.possibleValues, possibleValues);
    }

    @Override
    public ValueType getType() {
	return ValueType.ONE_OF_MANY;
    }

    @Override
    public String getValue() {
	return value;
    }

    @Override
    public String toString() {
	return value;
    }

    @Override
    public Status trySetting(String value) {
	int newValuePosition = possibleValues.indexOf(value);
	if (newValuePosition != -1) {
	    this.value = possibleValues.get(newValuePosition);
	} else {
	    return Status.error("ungültiger Wert „" + value + "“");
	}
	return Status.ok();
    }

    @Override
    public OneOfMany clone() {
	OneOfMany res = null;
	try {
	    res = (OneOfMany) super.clone();
	} catch (CloneNotSupportedException e) {
	    e.printStackTrace();
	    System.exit(-1);
	}
	res.value = new String(value);
	return res;
    }
}
