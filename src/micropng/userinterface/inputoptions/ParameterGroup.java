package micropng.userinterface.inputoptions;

public enum ParameterGroup implements ParameterGroupElement {
    CORE(null), INVOCATION_LINE(null);

    private ParameterGroup parentGroup;

    private ParameterGroup(ParameterGroup parentGroup) {
	this.parentGroup = parentGroup;
    }

    @Override
    public ParameterGroup getParentGroup() {
	return parentGroup;
    }
}
