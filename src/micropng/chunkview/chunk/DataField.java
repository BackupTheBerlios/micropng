package micropng.chunkview.chunk;

import micropng.commonlib.Queue;

public interface DataField {

    public Queue getStream();

    public byte[] getArray(int from, int length);

    public byte[] getArray();

    public int getSize();

    public int getByteAt(int pos);
}
