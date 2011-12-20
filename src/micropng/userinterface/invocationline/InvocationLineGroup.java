package micropng.userinterface.invocationline;

import java.util.ArrayList;

import micropng.userinterface.inputoptions.Parameter;
import micropng.userinterface.inputoptions.ParameterGroup;

public enum InvocationLineGroup {
    INVOCATION;

    private static ParameterGroup base;
    private static Parameter shortHelp = new Parameter(new InvocationShortHelp());
    private static Parameter longHelp = new Parameter(new InvocationLongHelp());

    static {
	String baseGroupName = "programmzeilenspezifische Parameter";
	ArrayList<Parameter> baseGroupParameters = new ArrayList<Parameter>();
	baseGroupParameters.add(shortHelp);
	baseGroupParameters.add(longHelp);
	ArrayList<ParameterGroup> baseGroupSubgroups = new ArrayList<ParameterGroup>();

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
