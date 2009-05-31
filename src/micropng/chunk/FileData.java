package micropng.chunk;

import micropng.MicroPNGQueue;

public class FileData implements DataField {

    private int size;

    @Override
    public int[] getArray(int start, int stop) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public int[] getArray() {
	return getArray(0, size);
    }

    @Override
    public int getSize() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public MicroPNGQueue getStream(int start, int stop) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public MicroPNGQueue getStream() {
	return getStream(0, size);
    }
}
