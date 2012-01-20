package micropng.userinterface.inputoptions;

public class RemoveUselessSBIT implements ParameterDescription {
    private static final String longHelp = "Mit Hilfe von sBIT-Chunks lässt sich angeben, wie die tatsächliche Farbtiefe einer Grafik von der verwendeten Farbtiefe abweicht. Gibt ein sBIT-Chunk die gleichen Werte für die tatsächliche Farbtiefe wie für die verwendete Farbtiefe an, kann er in der Regel gefahrlos gelöscht werden.";
    private static final String longParameterName = "no-useless-sbit";
    private static final String shortHelp = "irrelevante sBIT-Chunks entfernen";
    private static final char shortParameterName = 'S';
    private static final YesNoSwitch defaultValue = new YesNoSwitch();
    static {
	defaultValue.trySetting(true);
    }

    @Override
    public ParameterValue<?> defaultValue() {
	return defaultValue.clone();
    }

    @Override
    public String getLongHelp() {
	return longHelp;
    }

    @Override
    public String getLongParameterName() {
	return longParameterName;
    }

    @Override
    public String getShortHelp() {
	return shortHelp;
    }

    @Override
    public char getShortParameterName() {
	return shortParameterName;
    }
}
