package micropng.chunk;

import micropng.MicroPNGQueue;
import micropng.MicroPNGThread;

public class RAMData implements Data {

    private class QueueFeeder extends MicroPNGThread {

	private MicroPNGQueue out;
	private int from;
	private int to;

	public QueueFeeder(MicroPNGQueue out, int from, int to) {
	    this.out = out;
	    this.from = from;
	    this.to = to;
	}

	@Override
	public void run() {
	    for (int i = from; i < to; i++) {
		try {
		    out.put(data[i]);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	}
    }

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
	new Thread(new QueueFeeder(res, from, to)).run();
	return res;
    }

    @Override
    public MicroPNGQueue getStream() {
	return getStream(0, data.length);
    }
}
