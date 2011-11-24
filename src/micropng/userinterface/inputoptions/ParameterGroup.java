package micropng.userinterface.inputoptions;

import java.util.ArrayList;

public class ParameterGroup implements ParameterTreeNode {
    private ParameterGroupDescription description;
    private ArrayList<Parameter> parameters;
    private ArrayList<ParameterGroup> subGroups;

    public ParameterGroup(ParameterGroupDescription description, ArrayList<Parameter> parameters,
	    ArrayList<ParameterGroup> subGroups) {
	this.description = description;
	this.parameters = parameters;
	this.subGroups = subGroups;
    }

    public ParameterGroupDescription getDescription() {
	return description;
    }

    public ArrayList<Parameter> getParameters() {
	return parameters;
    }

    public ArrayList<ParameterGroup> getSubGroups() {
	return subGroups;
    }

    public ArrayList<Parameter> getAllDependingParameters() {
	ArrayList<Parameter> res = new ArrayList<Parameter>();
	res.addAll(parameters);
	for (ParameterGroup g : subGroups) {
	    res.addAll(g.getAllDependingParameters());
	}
	return res;
    }
}
