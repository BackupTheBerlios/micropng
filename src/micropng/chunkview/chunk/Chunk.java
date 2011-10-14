package micropng.chunkview.chunk;

public class Chunk {

    private int type;
    private DataField data;
    private int crc;

    public Chunk(int type, DataField data, int crc) {
	this.type = type;
	this.data = data;
	this.crc = crc;
    }

    public int getDataSize() {
	return data.getSize();
    }

    public int getType() {
	return type;
    }

    public DataField getData() {
	return data;
    }

    public int getCrc() {
	return crc;
    }

    public int getByteAt(int pos) {
	return data.getByteAt(pos);
    }
}
