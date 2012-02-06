package micropng.chunkview.chunk;

public final class Chunk {

    private final int type;
    private final DataField data;
    private final int crc;

    public Chunk(int type, DataField data, int crc) {
	this.type = type;
	this.data = data;
	this.crc = crc;
    }

    public final int getDataSize() {
	return data.getSize();
    }

    public final int getType() {
	return type;
    }

    public final DataField getData() {
	return data;
    }

    public final int getCrc() {
	return crc;
    }

    public final int getByteAt(int pos) {
	return data.getByteAt(pos);
    }
}
