package micropng.userinterface.invocationline;

import micropng.userinterface.inputoptions.Parameter;
import micropng.userinterface.inputoptions.ParameterGroup;

public enum InvocationLineGroup {
    INVOCATION;
    private static ParameterGroup base;
    private static Parameter shortHelp = new Parameter(new InvocationShortHelp());
    private static Parameter longHelp = new Parameter(new InvocationLongHelp());

    static {
	String baseGroupName = "programmzeilenspezifische Parameter";
	Parameter[] baseGroupParameters = new Parameter[] { shortHelp, longHelp };
	ParameterGroup[] baseGroupSubgroups = new ParameterGroup[] {};

	base = new ParameterGroup(baseGroupName, baseGroupParameters, baseGroupSubgroups);
    }

    public ParameterGroup getGroup() {
	return base;
    }

    public static Parameter getLongHelpInstance() {
	return longHelp;
    }

    public static Parameter getShortHelpInstance() {
	return shortHelp;
    }
}
