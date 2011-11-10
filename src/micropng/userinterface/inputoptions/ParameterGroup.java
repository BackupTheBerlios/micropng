package micropng.userinterface.inputoptions;

public enum ParameterGroup implements ParameterGroupElement {
    TOP_LEVEL(null),
    INVOCATION_LINE(TOP_LEVEL),
    CORE(TOP_LEVEL),
    CHUNK_VIEW(CORE);

    private ParameterGroup(ParameterGroup parentGroup) {

    }
}
