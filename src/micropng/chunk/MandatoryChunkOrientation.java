package micropng.chunk;

public enum MandatoryChunkOrientation {
    IHDR(null),
    cHRM(ChunkType.IHDR),
    gAMA(ChunkType.IHDR),
    iCCP(ChunkType.IHDR),
    iTXt(ChunkType.IHDR),
    pHYs(ChunkType.IHDR),
    sBIT(ChunkType.IHDR),
    sPLT(ChunkType.IHDR),
    sRGB(ChunkType.IHDR),
    tEXt(ChunkType.IHDR),
    tIME(ChunkType.IHDR),
    zTXt(ChunkType.IHDR),
    PLTE(ChunkType.IHDR),
    bKGD(ChunkType.PLTE),
    hIST(ChunkType.PLTE),
    tRNS(ChunkType.PLTE),
    IDAT(ChunkType.PLTE),
    IEND(ChunkType.IDAT);

    private final ChunkType orientation;

    MandatoryChunkOrientation(ChunkType t) {
	this.orientation = t;
    }

    public ChunkType getOrientation() {
	return orientation;
    }
}
