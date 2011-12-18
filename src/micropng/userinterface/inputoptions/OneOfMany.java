package micropng.userinterface.inputoptions;

import micropng.commonlib.Status;

public class OneOfMany implements ParameterValue<Enum<?>> {
    Enum<?> value;

    @Override
    public ValueType getType() {
	return ValueType.ONE_OF_MANY;
    }

    @Override
    public Enum<?> getValue() {
	return value;
    }

    @Override
    public Status trySetting(Enum<?> value) {

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
	res.value = value;
	return res;
    }
}
