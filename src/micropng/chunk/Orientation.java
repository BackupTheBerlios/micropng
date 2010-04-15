package micropng.chunk;

import java.util.ArrayList;

public enum Orientation {
    IHDR(null),
    cHRM(Type.IHDR),
    gAMA(Type.IHDR),
    iCCP(Type.IHDR),
    iTXt(Type.IHDR),
    pHYs(Type.IHDR),
    sBIT(Type.IHDR),
    sPLT(Type.IHDR),
    sRGB(Type.IHDR),
    tEXt(Type.IHDR),
    tIME(Type.IHDR),
    zTXt(Type.IHDR),
    PLTE(Type.IHDR),
    bKGD(Type.PLTE),
    hIST(Type.PLTE),
    tRNS(Type.PLTE),
    IDAT(Type.PLTE),
    IEND(Type.IDAT);

    private final Type orientation;

    Orientation(Type t) {
	this.orientation = t;
    }

    public Type getOrientation() {
	return orientation;
    }

    public static Orientation valueOf(int type) {
	return valueOf(Type.stringValue(type));
    }

    public static ArrayList<Type> chainOfOrientation(Type t) {
	ArrayList<Type> res;

	if (t == null) {
	    res = new ArrayList<Type>();
	} else {
	    Type orientationOfT = Orientation.valueOf(t.name()).orientation;
	    res = Orientation.chainOfOrientation(orientationOfT);
	    res.add(t);
	}

	return res;
    }

    public static boolean beforeIDAT(Type t) {
	return chainOfOrientation(t).contains(Type.IDAT);
    }
}
