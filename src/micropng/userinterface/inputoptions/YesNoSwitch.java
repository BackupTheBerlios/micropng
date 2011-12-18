package micropng.userinterface.inputoptions;

import micropng.commonlib.Status;

public class YesNoSwitch implements ParameterValue<Boolean> {

    private Boolean value;

    @Override
    public ValueType getType() {
	return ValueType.YES_NO_SWITCH;
    }

    @Override
    public Boolean getValue() {
	return value;
    }

    @Override
    public String toString() {
	return value.booleanValue()? "y" : "n";
    }

    @Override
    public Status trySetting(Boolean value) {
	this.value = value;
	return Status.ok();
    }

    @Override
    public YesNoSwitch clone() {
	YesNoSwitch res = null;
	try {
	    res = (YesNoSwitch) super.clone();
	} catch (CloneNotSupportedException e) {
	    e.printStackTrace();
	    System.exit(-1);
	}
	res.value = value.booleanValue();
	return res;
    }
}
