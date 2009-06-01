package micropng.chunk;

import java.io.IOException;
import java.io.RandomAccessFile;

import micropng.MicroPNGQueue;
import micropng.MicroPNGThread;

public class FileData implements Data {
    private class QueueFeeder extends MicroPNGThread {

	private MicroPNGQueue out;
	private int from;
	private int length;

	public QueueFeeder(MicroPNGQueue out, int from, int length) {
	    this.out = out;
	    this.from = from;
	    this.length = length;
	}

	@Override
	public void run() {
	    try {
		file.seek(start + from);

		for (int i = 0; i < length; i++) {
		    out.put(file.readByte());
		}
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    private RandomAccessFile file;
    private long start;
    private int size;

    public FileData(RandomAccessFile file, long start, int size) {
	this.file = file;
	this.start = start;
	this.size = size;
    }

    @Override
    public byte[] getArray(int from, int length) {
	byte[] res = new byte[length];
	try {
	    // TODO: make file reads thread safe
	    file.seek(start + from);
	    file.readFully(res, 0, length);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return res;
    }

    @Override
    public byte[] getArray() {
	return getArray(0, size);
    }

    @Override
    public int getSize() {
	return size;
    }

    @Override
    public MicroPNGQueue getStream(int from, int length) {
	MicroPNGQueue res = new MicroPNGQueue();
	new Thread(new QueueFeeder(res, from, length)).run();
	return null;
    }

    @Override
    public MicroPNGQueue getStream() {
	return getStream(0, size);
    }
}
