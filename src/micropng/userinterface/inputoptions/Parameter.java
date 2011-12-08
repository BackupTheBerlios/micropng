package micropng.userinterface.inputoptions;

public class Parameter {
    private ParameterDescription description;
    private ParameterValue<?> value;

    public Parameter(ParameterDescription description, ParameterValue<?> value) {
        this.description = description;
        this.value = value;
    }

    public ParameterDescription getDescription() {
        return description;
    }

    public ParameterValue<?> getValue() {
        return value;
    }

    public ValueType getValueType() {
	return value.getType();
    }
}
