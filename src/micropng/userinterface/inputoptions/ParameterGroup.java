package micropng.userinterface.inputoptions;

public enum ParameterGroup implements ParameterGroupElement {
    TOP_LEVEL(null),
    CHUNK_VIEW(TOP_LEVEL);

    private ParameterGroup(ParameterGroup parentGroup) {

    }
}
