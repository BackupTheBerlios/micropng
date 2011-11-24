package micropng.userinterface.inputoptions;

public class ParameterTree {
    ParameterTreeDescription description;
    private ParameterTreeNode root;

    public ParameterTree(ParameterTreeDescription description, ParameterTreeNode root) {
	this.description = description;
	this.root = root;
    }
}
