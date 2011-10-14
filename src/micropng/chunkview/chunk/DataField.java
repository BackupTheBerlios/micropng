package micropng.chunkview.chunk;

public interface DataField extends Data {

    // public Queue getStream(int from, int length);

    public byte[] getArray(int from, int length);

    public byte[] getArray();

    public int getSize();

    public int getByteAt(int pos);
}
