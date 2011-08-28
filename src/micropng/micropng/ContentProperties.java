package micropng.micropng;

public class ContentProperties {
    private enum Transparency {
	NONE, FULL, ALPHA
    };

    private boolean isCorrupt;
    // theoretically, there could be more colors than fit into long
    private long numberOfColors;
    private boolean isGreyscale;
    private int bitDepth;
    private boolean hasUnknownMandatoryChunks;
    private boolean hasUnknownAncillaryChunks;
}
