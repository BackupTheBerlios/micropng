package micropng.userinterface.inputoptions;

import micropng.commonlib.Status;

public class YesNoSwitch implements ParameterValue<Boolean> {

    private Boolean value;

    @Override
    public Boolean getValue() {
	return value;
    }

    @Override
    public Status trySetting(Boolean value) {
	this.value = value;
	return Status.ok();
    }
}
