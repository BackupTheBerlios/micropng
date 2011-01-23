package micropng.chunkview.chunk;

import micropng.commonlib.Queue;

public interface Data {

    public int getSize();

//    public Queue getStream(int from, int length);

    public Queue getStream();

    public byte[] getArray(int from, int length);

    public byte[] getArray();

    public int getByteAt(int pos);
}
