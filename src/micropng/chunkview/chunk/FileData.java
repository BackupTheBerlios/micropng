package micropng.chunkview.chunk;

import java.io.IOException;
import java.io.RandomAccessFile;

import micropng.commonlib.Queue;
import micropng.micropng.MicropngThread;

public class FileData implements Data {
    private class QueueFeeder extends MicropngThread {

	private Queue out;

	public QueueFeeder(Queue out) {
	    this.out = out;
	}

	@Override
	public void run() {
	    try {
		file.seek(start);

		for (int i = 0; i < size; i++) {
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
    public Queue getStream() {
	Queue res = new Queue();
	new Thread(new QueueFeeder(res)).run();
	return res;
    }

    @Override
    public int getByteAt(int pos) {
	try {
	    file.seek(start + pos);
	    file.read();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return 0;
    }
}