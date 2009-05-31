package micropng.chunk;

import micropng.MicroPNGQueue;

public interface DataField {

    public int getSize();
    public MicroPNGQueue getStream(int start, int stop);
    public MicroPNGQueue getStream();
    public int[] getArray(int start, int stop);
    public int[] getArray();
}
