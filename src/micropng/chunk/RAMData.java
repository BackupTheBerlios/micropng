package micropng.chunk;

import micropng.Queue;
import micropng.MicropngThread;

public class RAMData implements Data {

    private class QueueFeeder extends MicropngThread {

	private Queue out;
	private int from;
	private int length;

	public QueueFeeder(Queue out, int from, int length) {
	    this.out = out;
	    this.from = from;
	    this.length = length;
	}

	@Override
	public void run() {
	    for (int i = from; i < length; i++) {
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
    public byte[] getArray(int from, int length) {
	byte[] res = new byte[length];
	System.arraycopy(data, from, res, 0, length);
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
    public Queue getStream(int from, int length) {
	Queue res = new Queue();
	new Thread(new QueueFeeder(res, from, length)).run();
	return res;
    }

    @Override
    public Queue getStream() {
	return getStream(0, data.length);
    }
}
