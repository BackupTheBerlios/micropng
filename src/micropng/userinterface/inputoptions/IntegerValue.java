package micropng.userinterface.inputoptions;

import micropng.commonlib.Status;

public class IntegerValue implements ParameterValue<Long> {

    private long value;
    private long lowerBound;
    private long upperBound;

    public IntegerValue() {
	this.lowerBound = Long.MIN_VALUE;
	this.upperBound = Long.MAX_VALUE;
    }

    public IntegerValue(long lowerBound, long upperBound) {
	this.lowerBound = lowerBound;
	this.upperBound = upperBound;
    }

    @Override
    public ValueType getType() {
	return ValueType.INTEGER_VALUE;
    }

    @Override
    public Long getValue() {
	return value;
    }

    @Override
    public String toString() {
	return Long.toString(value);
    }
    
    @Override
    public Status trySetting(Long value) {
	if (value < lowerBound) {
	    return Status.error("Wert zu klein");
	}
	if (value > upperBound) {
	    return Status.error("Wert zu groß");
	}

	this.value = value;
	return Status.ok();
    }
}
