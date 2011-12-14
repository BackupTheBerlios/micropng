package micropng.userinterface.invocationline;

import micropng.userinterface.inputoptions.Parameter;
import micropng.userinterface.inputoptions.ParameterGroup;

public enum InvocationLineGroup {
    INVOCATION;
    private static ParameterGroup base;
    private static Parameter shortHelp = InvocationShortHelp.instance();
    private static Parameter longHelp = InvocationLongHelp.instance();

    static {
	String baseGroupName = "allgemeine Steuerung des Programms";
	Parameter[] baseGroupParameters = new Parameter[] { longHelp, shortHelp };
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
