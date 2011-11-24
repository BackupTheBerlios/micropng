package micropng.userinterface.inputoptions;

import micropng.fileview.PNGProperties;

public class CondenseIDATChunks implements ParameterDescription {
    private static final String longHelp = "Vorhandene IDAT-Chunks werden in möglichst wenige zusammengefasst, die jeweils maximal " + PNGProperties.getMaxSize() + "Bytes lang sind.";
    private static final String longParameterName = "condense-idat";
    private static final String shortHelp = "IDAT-Chunks zusammenfassen";
    private static final char shortParameterName = 'a';

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

    @Override
    public Parameter instantiate() {
	YesNoSwitch value = new YesNoSwitch();
	Parameter res = new Parameter(this, value);
	value.trySetting(true);
	return res;
    }
}
