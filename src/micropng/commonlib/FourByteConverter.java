package micropng.commonlib;

public class FourByteConverter {
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

    public static byte[] byteArrayValue(int integer) {
	byte[] res = new byte[4];
	for (int i = 0; i < 4; i++) {
	    res[3 - i] = (byte) (integer & 0xff);
	    integer >>= 8;
	}
	return res;
    }
}
