package micropng.chunk;

import micropng.MicroPNGQueue;

public interface Data {

    public int getSize();
    public MicroPNGQueue getStream(int from, int length);
    public MicroPNGQueue getStream();
    public byte[] getArray(int from, int length);
    public byte[] getArray();
}
