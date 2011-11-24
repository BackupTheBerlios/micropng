package micropng.userinterface.inputoptions;

import java.util.ArrayList;

public enum ParameterGroupDescription {
    BASE, FILE_VIEW;

    private ParameterDescription[] parameterDescriptions;
    private ParameterGroupDescription[] subGroupsDescriptions;

    static {
	BASE.parameterDescriptions = new ParameterDescription[] { new InputFilename() };
	BASE.subGroupsDescriptions = new ParameterGroupDescription[] { FILE_VIEW };

	FILE_VIEW.parameterDescriptions = new ParameterDescription[] { new SortChunks(),
		new CondenseIDATChunks() };
	FILE_VIEW.subGroupsDescriptions = new ParameterGroupDescription[] {};
    }

    public ParameterGroup instantiate() {
	ArrayList<Parameter> parameters = new ArrayList<Parameter>();
	ArrayList<ParameterGroup> subGroups = new ArrayList<ParameterGroup>();

	for (ParameterDescription description : parameterDescriptions) {
	    parameters.add(description.instantiate());
	}

	for (ParameterGroupDescription description : subGroupsDescriptions) {
	    subGroups.add(description.instantiate());
	}

	ParameterGroup res = new ParameterGroup(this, parameters, subGroups);
	return res;
    }
}
