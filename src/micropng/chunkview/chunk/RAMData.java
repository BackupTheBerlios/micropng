package micropng.chunkview.chunk;

import micropng.commonlib.Queue;

public class RAMData implements DataField {

    private class QueueFeeder implements Runnable {

	private Queue out;

	public QueueFeeder(Queue out) {
	    this.out = out;
	}

	@Override
	public void run() {
	    for (int i = 0; i < data.length; i++) {
		out.put(data[i]);
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
	new Thread(new QueueFeeder(res)).start();
	return res;
    }

    @Override
    public int getByteAt(int pos) {
	return data[pos];
    }
}
