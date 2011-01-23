package micropng.chunkview.chunk;

import micropng.commonlib.Queue;
import micropng.micropng.MicropngThread;

public class RAMData implements Data {

    private class QueueFeeder extends MicropngThread {

	private Queue out;

	public QueueFeeder(Queue out) {
	    this.out = out;
	}

	@Override
	public void run() {
	    for (int i = 0; i < data.length; i++) {
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
    public Queue getStream() {
	Queue res = new Queue();
	new Thread(new QueueFeeder(res)).run();
	return res;
    }

    @Override
    public int getByteAt(int pos) {
	return data[pos];
    }
}
