package micropng.chunk;

import micropng.MicroPNGQueue;

public interface Data {

    public int getSize();
    public MicroPNGQueue getStream(int start, int stop);
    public MicroPNGQueue getStream();
    public byte[] getArray(int start, int stop);
    public byte[] getArray();
}
