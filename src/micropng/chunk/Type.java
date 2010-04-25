package micropng.chunk;

import java.util.HashSet;

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
	intType = intValue(name());
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

    public static String stringValue(int type) {
	char[] tmp = new char[4];
	for (int i = 0; i < 4; i++) {
	    tmp[3 - i] = (char) (type & 0xff);
	    type >>= 8;
	}
	return new String(tmp);
    }

    public static int intValue(String type) {
	int res = 0;
	for (int i = 0; i < 4; i++) {
	    res <<= 8;
	    res += type.charAt(i);
	}
	return res;
    }

    public static int[] intArrayValue(int type) {
	int[] res = new int[4];
	for (int i = 0; i < 4; i++) {
	    res[3 - i] = (char) (type & 0xff);
	    type >>= 8;
	}
	return res;
    }

    public static Type valueOf(int type) {
	return valueOf(stringValue(type));
    }
}
