package micropng.pngio;

public enum PNGProperties {
    ;
    private static final byte[] sig = { -119, 'P', 'N', 'G', 13, 10, 26, 10 };

    public static byte[] getSignature() {
	return sig;
    }
}
