package micropng.encodingview;

public enum InterlaceMethod {

    NONE, ADAM7;

    public static InterlaceMethod getMethod(int i) {
	return InterlaceMethod.values()[i];
    }
}
