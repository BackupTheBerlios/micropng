package micropng.userinterface.inputoptions;

import micropng.userinterface.invocationline.InvocationHelp;

public enum ParameterType {
    INVOCATION_HELP(new InvocationHelp()),
    INPUT_FILENAME(new InputFilename()),
    SORT_CHUNKS(new SortChunks());

    private ParameterDescription parameterObject;

    private ParameterType(ParameterDescription optionObject) {
	this.parameterObject = optionObject;
    }

    public ParameterDescription getParameterObject() {
	return parameterObject;
    }
}
