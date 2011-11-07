package micropng.userinterface.inputoptions;

public enum Parameter {
    INPUT_FILENAME(new InputFilename());

    private ParameterDefinition parameterObject;

    private Parameter(ParameterDefinition optionObject) {
	this.parameterObject = optionObject;
    }

    public ParameterDefinition getParameterObject() {
	return parameterObject;
    }
}
