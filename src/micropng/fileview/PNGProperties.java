package micropng.fileview;

public enum PNGProperties {
    ;
    private static final byte[] sig = { -119, 'P', 'N', 'G', 13, 10, 26, 10 };
    private static int maxSize = (int) ((1l << 31) - 1);

    public static byte[] getSignature() {
	return sig;
    }

    public static int getMaxSize() {
	return maxSize;
    }
}
