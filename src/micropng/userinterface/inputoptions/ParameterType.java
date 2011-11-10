package micropng.userinterface.inputoptions;

public enum ParameterType {
    INVOCATION_HELP(new InvocationHelp()),
    INPUT_FILENAME(new InputFilename()),
    SORT_CHUNKS(new SortChunks());

    private Parameter parameterObject;

    private ParameterType(Parameter optionObject) {
	this.parameterObject = optionObject;
    }

    public Parameter getParameterObject() {
	return parameterObject;
    }
}
