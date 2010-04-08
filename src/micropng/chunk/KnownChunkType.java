package micropng.chunk;

import java.util.HashSet;

public enum KnownChunkType {
    bKGD, cHRM, gAMA, hIST, iCCP, IHDR, IDAT, IEND, iTXt, pHYs, PLTE, sBIT, sPLT, sRGB, tEXt, tIME, tRNS, zTXt;

    private int intType;
    private static final HashSet<String> lookUpTable;

    static {
	KnownChunkType[] values = KnownChunkType.values();
	lookUpTable = new HashSet<String>(values.length * 2);

	for (KnownChunkType type : values) {
	    lookUpTable.add(type.name());
	}
    }

    private KnownChunkType() {
	intType = Type.intValue(name());
    }

    public int intValue() {
	return intType;
    }

    public static boolean isKnown(int type) {
	return lookUpTable.contains(Type.stringValue(type));
    }
}
