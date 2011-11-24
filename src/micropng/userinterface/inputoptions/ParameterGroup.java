package micropng.userinterface.inputoptions;

import java.util.ArrayList;

public class ParameterGroup implements ParameterTreeNode {
    private ParameterGroupDescription description;
    private ArrayList<ParameterTreeNode> childNodes;

    public ParameterGroup(ParameterGroupDescription description, ArrayList<ParameterTreeNode> childNodes) {
	this.description = description;
	this.childNodes = childNodes;
    }
}
