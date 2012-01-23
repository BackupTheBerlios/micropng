package micropng.chunkview.chunk;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

import micropng.commonlib.Queue;

public class FileData implements DataField {
    private class QueueFeeder implements Runnable {
	private Queue out;

	public QueueFeeder(Queue out) {
	    this.out = out;
	}

	@Override
	public void run() {
	    try {
		synchronized (this) {
		    fileChannel.position(start);

		    for (int i = 0; i < size; i++) {
			out.put(dataInputStream.readUnsignedByte());
		    }
		}
		out.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    private DataInputStream dataInputStream;
    private FileChannel fileChannel;
    private long start;
    private int size;

    public FileData(File file, long start, int size) throws FileNotFoundException {
	FileInputStream  fileInputStream = new FileInputStream(file);
	this.dataInputStream = new DataInputStream(fileInputStream);
	this.fileChannel = fileInputStream.getChannel();
	this.start = start;
	this.size = size;
    }

    @Override
    public byte[] getArray(int from, int length) {
	byte[] res = new byte[length];
	try {
	    synchronized (this) {
		fileChannel.position(start + from);
		dataInputStream.readFully(res, 0, length);
	    }
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
	new Thread(new QueueFeeder(res)).start();
	return res;
    }

    @Override
    public int getByteAt(int pos) {
	int res = -1;
	try {
	    synchronized (this) {
		fileChannel.position(start + pos);
		res = dataInputStream.readUnsignedByte();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return res;
    }
}
