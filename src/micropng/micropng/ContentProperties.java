package micropng.micropng;

public class ContentProperties {
    private enum Transparency {
	NONE, FULL, ALPHA
    };

    // theoretically, there could be more colors than fit into long
    private long numberOfColors;
    private boolean greyscale;
    private int bitDepth;
    private boolean unknownMandatoryChunks;
    private boolean unknownAncillaryChunks;
    long height;
    long width;
    private Transparency transparency;
}
