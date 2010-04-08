package micropng.chunk;

public enum MandatoryChunkOrientation {
    IHDR(null),
    cHRM(MandatoryChunkOrientation.ihdr),
    gAMA(MandatoryChunkOrientation.ihdr),
    iCCP(MandatoryChunkOrientation.ihdr),
    iTXt(MandatoryChunkOrientation.ihdr),
    pHYs(MandatoryChunkOrientation.ihdr),
    sBIT(MandatoryChunkOrientation.ihdr),
    sPLT(MandatoryChunkOrientation.ihdr),
    sRGB(MandatoryChunkOrientation.ihdr),
    tEXt(MandatoryChunkOrientation.ihdr),
    tIME(MandatoryChunkOrientation.ihdr),
    zTXt(MandatoryChunkOrientation.ihdr),
    PLTE(MandatoryChunkOrientation.ihdr),
    bKGD(MandatoryChunkOrientation.plte),
    hIST(MandatoryChunkOrientation.plte),
    tRNS(MandatoryChunkOrientation.plte),
    IDAT(MandatoryChunkOrientation.plte),
    IEND(MandatoryChunkOrientation.idat);

    private final static String ihdr = "IHDR";
    private final static String plte = "PLTE";
    private final static String idat = "IDAT";

    private final String orientation;

    MandatoryChunkOrientation(String orientation) {
	this.orientation = orientation;
    }

    public String getOrientation() {
	return orientation;
    }
}
