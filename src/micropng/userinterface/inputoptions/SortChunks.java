package micropng.userinterface.inputoptions;

import java.util.ArrayList;

import micropng.commonlib.Status;

public class SortChunks implements ParameterDescription {
    private static final String longHelp = "Vorhandene und ggf. neue Chunks werden nach implementierungsspezifischen Regeln sortiert. In einigen Fällen kann auf diese Weise die Kompression einer späteren Archivierung verbessert werden, insbesondere wenn mehrere png-Dateien zusammen gepackt werden.";
    private static final String longParameterName = "sort-chunks";
    private static final String shortHelp = "Chunks sortieren";
    private static final char shortParameterName = 's';

    @Override
    public String getLongHelp() {
	return longHelp;
    }

    @Override
    public String getLongParameterName() {
	return longParameterName;
    }

    @Override
    public ParameterGroup getParentGroup() {
	return ParameterGroup.CHUNK_VIEW;
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