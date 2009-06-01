package micropng.chunk;

public class Chunk {

    private Type type;
    private Data data;
    private int crc;

    public Chunk(Type type, Data data, int crc) {
	this.type = type;
	this.data = data;
	this.crc = crc;
    }

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
