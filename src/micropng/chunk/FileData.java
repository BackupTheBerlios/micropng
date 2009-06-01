package micropng.chunk;

import java.io.IOException;
import java.io.RandomAccessFile;

import micropng.MicroPNGQueue;
import micropng.MicroPNGThread;

public class FileData implements Data {
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
		    out.put(file.readByte());
		} catch (InterruptedException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
    }

    private RandomAccessFile file;
    private int start;
    private int end;

    public FileData(RandomAccessFile file, int start, int end) {
	this.file = file;
	this.start = start;
	this.end = end;
    }

    @Override
    public byte[] getArray(int from, int to) {
	byte[] res = new byte[to - from];
	try {
	    file.read(res, from, to - from);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return res;
    }

    @Override
    public byte[] getArray() {
	return getArray(0, end - start);
    }

    @Override
    public int getSize() {
	return end - start;
    }

    @Override
    public MicroPNGQueue getStream(int from, int to) {
	MicroPNGQueue res = new MicroPNGQueue();
	new Thread(new QueueFeeder(res, from, to)).run();
	return null;
    }

    @Override
    public MicroPNGQueue getStream() {
	return getStream(0, end - start);
    }
}
