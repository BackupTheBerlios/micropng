package micropng.micropng;

public class ContentProperties {
    private enum Transparency {
	NONE, FULL, ALPHA
    }

    // theoretically, there could be more colors than fit into long
    private long numberOfColors;
    private int bitDepth;
    private Dimensions size;
    private Transparency transparency;
    private boolean greyscale;
    private boolean unknownMandatoryChunks;
    private boolean unknownAncillaryChunks;
}
