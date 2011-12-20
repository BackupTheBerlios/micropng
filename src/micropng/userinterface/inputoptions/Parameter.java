package micropng.userinterface.inputoptions;

public class Parameter {
    private ParameterDescription description;
    private ParameterValue<?> value;

    public Parameter(ParameterDescription description) {
	this.description = description;
	this.value = description.defaultValue();
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

    public <U> U take() {
	// suppress warning in preliminary solution, not really satisfying
	@SuppressWarnings("unchecked")
	U res = (U) value.getValue();
	return res;
    }

    public ValueType getValueType() {
	return value.getType();
    }

    public ParameterValue<?> defaultValue() {
	return description.defaultValue();
    }
}
