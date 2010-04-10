package micropng.chunk;

public class Chunk {

    private static int nextId = 0;

    private Type type;
    private Data data;
    private int crc;
    private ChunkBehaviour behaviour;
    private int id;
    

    public Chunk(Type type, Data data, int crc) {
	this.type = type;
	this.data = data;
	this.crc = crc;
	this.id = nextId;
	nextId++;
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

    public byte[] getDataByteArray() {
	return data.getArray();
    }
    
    public ChunkBehaviour getBehaviour() {
        return behaviour;
    }

    public int getId() {
        return id;
    }
    
    public int getTypeInt() {
	return type.toInt();
    }

    public byte[] getTypeByteArray() {
	return intToByteArray(type.toInt());
    }

    public int getSize() {
	return data.getSize() + 12;
    }

    public byte[] getDataSizeByteArray() {
	return intToByteArray(data.getSize());
    }

    public int getCrc() {
	return crc;
    }

    public byte[] getCrcByteArray() {
	return intToByteArray(crc);
    }

    private byte[] intToByteArray(int integer) {
	byte[] res = new byte[4];
	int tmp = integer;
	for (int i = 0; i < 4; i++) {
	    res[3 - i] = (byte) (tmp & 0xff);
	    tmp >>= 8;
	}
	return res;
    }

    public int getByteAt(int pos) {
	return data.getByteAt(pos);
    }

    public int getDataSize() {
	return data.getSize();
    }

    public boolean isType(String s) {
	return type.toString().equals(s);
    }
}
