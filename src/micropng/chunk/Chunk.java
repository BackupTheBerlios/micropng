package micropng.chunk;

public class Chunk {

    private Data data;
    private Type type;
    private int crc;

    public boolean isAncillary() {
	return type.isAncillary();
    }

    public boolean isPrivate() {
	return type.isPrivate();
    }

    public boolean isThirdByteUpperCase() {
	return type.isThirdByteUpperCase();
    }

    public boolean isSafeToCopy() {
	return type.isSafeToCopy();
    }

    public Data getData() {
	return data;
    }

    public int getTypeInt() {
	return type.toInt();
    }
}
