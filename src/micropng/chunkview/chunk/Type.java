package micropng.chunkview.chunk;

import java.util.HashSet;

import micropng.commonlib.FourByteConverter;

public enum Type {
    bKGD,
    cHRM,
    gAMA,
    hIST,
    iCCP,
    IHDR,
    IDAT,
    IEND,
    iTXt,
    pHYs,
    PLTE,
    sBIT,
    sPLT,
    sRGB,
    tEXt,
    tIME,
    tRNS,
    zTXt;

    private int intType;
    private static final HashSet<Integer> lookUpTable;

    static {
	Type[] values = Type.values();
	lookUpTable = new HashSet<Integer>(values.length * 2);

	for (Type type : values) {
	    lookUpTable.add(type.intType);
	}
    }

    private Type() {
	intType = FourByteConverter.intValue(name());
    }

    public int toInt() {
	return intType;
    }

    public static boolean isKnown(int type) {
	return lookUpTable.contains(type);
    }

    public static boolean isAncillary(int type) {
	return (type & 0x20000000) != 0;
    }

    public static boolean isPrivate(int type) {
	return (type & 0x00200000) != 0;
    }

    public static boolean isThirdByteUpperCase(int type) {
	return (type & 0x00002000) != 0;
    }

    public static boolean isSafeToCopy(int type) {
	return (type & 0x00000020) != 0;
    }

    public static Type valueOf(int type) {
	return valueOf(FourByteConverter.stringValue(type));
    }
}
