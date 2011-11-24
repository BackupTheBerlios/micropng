package micropng.userinterface.inputoptions;

import java.util.ArrayList;

public enum ParameterGroupDescription implements Description {
    BASE, FILE_VIEW;

    private ParameterDescription[] parameters;
    private ParameterGroupDescription[] subGroups;

    static {
	BASE.parameters = new ParameterDescription[] { new InputFilename() };
	BASE.subGroups = new ParameterGroupDescription[] { FILE_VIEW };

	FILE_VIEW.parameters = new ParameterDescription[] { new SortChunks(), new CondenseIDATChunks() };
	FILE_VIEW.subGroups = new ParameterGroupDescription[] {};
    }

    public ParameterGroup instantiate() {
	ArrayList<ParameterTreeNode> instantiatedNodes = new ArrayList<ParameterTreeNode>();

	for (ParameterGroupDescription description : subGroups) {
	    instantiatedNodes.add(description.instantiate());
	}

	for (ParameterDescription description : parameters) {
	    instantiatedNodes.add(description.instantiate());
	}

	ParameterGroup res = new ParameterGroup(this, instantiatedNodes);
	return res;
    }
}
