package micropng.userinterface.inputoptions;

public enum ParameterTreeDescription {
    DEFAULT;

    public ParameterTree instantiate() {
	ParameterGroup rootNode = ParameterGroupDescription.BASE.instantiate();
	ParameterTree res = new ParameterTree(this, rootNode);
	return res;
    }
}
