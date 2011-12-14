package micropng.userinterface.inputoptions;

public class Parameter {
    private ParameterDescription description;
    private ParameterValue<?> value;

    public Parameter(ParameterDescription description, ParameterValue<?> value) {
	this.description = description;
	this.value = value;
    }

    public String getLongHelp() {
	return description.getLongHelp();
    }

    public String getLongParameterName() {
	return description.getLongParameterName();
    }

    public String getShortHelp() {
	return description.getShortHelp();
    }

    public char getShortParameterName() {
	return description.getShortParameterName();
    }

    public ParameterValue<?> getValue() {
	return value;
    }

    public ValueType getValueType() {
	return value.getType();
    }
}
