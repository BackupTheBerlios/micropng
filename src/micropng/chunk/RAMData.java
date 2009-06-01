package micropng.chunk;

import micropng.MicroPNGQueue;

public class RAMData implements Data {

    private byte[] data;

    public RAMData(byte[] data) {
	this.data = data;
    }

    @Override
    public byte[] getArray(int from, int to) {
	byte[] res = new byte[to - from];
	System.arraycopy(data, from, res, 0, to - from);
	return res;
    }

    @Override
    public byte[] getArray() {
	return data.clone();
    }

    @Override
    public int getSize() {
	return data.length;
    }

    @Override
    public MicroPNGQueue getStream(int from, int to) {
	MicroPNGQueue res = new MicroPNGQueue();
	// TODO: implement
	return null;
    }

    @Override
    public MicroPNGQueue getStream() {
	return getStream(0, data.length);
    }
}
