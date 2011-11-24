package micropng.userinterface.inputoptions;

public class ParameterTree {
    ParameterTreeDescription description;
    private ParameterGroup root;

    public ParameterTree(ParameterTreeDescription description, ParameterGroup root) {
	this.description = description;
	this.root = root;
    }

    public ParameterGroup getRootNode() {
	return root;
    }
}
