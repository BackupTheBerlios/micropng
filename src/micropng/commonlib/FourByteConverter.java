package micropng.commonlib;

import java.nio.charset.Charset;

public class FourByteConverter {
    private static final Charset charset = Charset.forName("US-ASCII");

    public static String stringValue(int type) {
	return new String(byteArrayValue(type), charset);
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
	    res[3 - i] = type & 0xff;
	    type >>= 8;
	}
	return res;
    }

    public static byte[] byteArrayValue(int integer) {
	byte[] res = new byte[4];
	for (int i = 0; i < 4; i++) {
	    res[3 - i] = (byte) integer;
	    integer >>= 8;
	}
	return res;
    }
}
