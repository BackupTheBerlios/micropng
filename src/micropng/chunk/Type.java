package micropng.chunk;

public class Type {

    private int value;

    public Type(int type) {
	this.value = type;
    }

    public Type(String name) {
	this.value = intValue(name);
    }

    public boolean isAncillary() {
	return (value & 0x10000000) != 0;
    }

    public boolean isPrivate() {
	return (value & 0x00100000) != 0;
    }

    public boolean isThirdByteUpperCase() {
	return (value & 0x00001000) != 0;
    }

    public boolean isSafeToCopy() {
	return (value & 0x00000010) != 0;
    }

    public boolean isKnown() {
	return KnownChunkType.isKnown(value);
    }

    public int toInt() {
	return value;
    }

    public static String stringValue(int name) {
	char[] tmp = new char[4];
	for (int i = 0; i < 4; i++) {
	    tmp[3 - i] = (char) (name & 0xff);
	    name >>= 8;
	}
	return new String(tmp);
    }

    public static int intValue(String name) {
	int res = 0;
	for (int i = 0; i < 4; i++) {
	    res <<= 8;
	    res += name.charAt(i);
	}
	return res;
    }
}
