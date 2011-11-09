package micropng.userinterface.inputoptions;

public enum ParameterType {
    INPUT_FILENAME(new InputFilename()),
    SORT_CHUNKS(new SortChunks()),
    INVOCATION_HELP(new InvocationHelp());

    private Parameter parameterObject;

    private ParameterType(Parameter optionObject) {
	this.parameterObject = optionObject;
    }

    public Parameter getParameterObject() {
	return parameterObject;
    }
}
