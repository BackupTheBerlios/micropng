package micropng.chunk;

public class Type {

    private int value;

    public Type(int type) {
	this.value = type;
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

    public int toInt() {
	return value;
    }

}
