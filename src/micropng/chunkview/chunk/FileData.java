package micropng.chunkview.chunk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

import micropng.commonlib.Queue;

public class FileData implements DataField {
    private class QueueFeeder implements Runnable {
	private final Queue out;
	private static final int bufferSize = 2048;

	public QueueFeeder(Queue out) {
	    this.out = out;
	}

	@Override
	public void run() {
	    try {
		synchronized (this) {
		    final byte[] buffer = new byte[bufferSize];
		    final int localSize = size;
		    int maximumLengthToRead = bufferSize;
		    int bytesRead = 0;
		    
		    fileChannel.position(start);

		    while (bytesRead < localSize) {
			int lastReadCount;

			if (localSize - bytesRead < bufferSize) {
			    maximumLengthToRead = localSize - bytesRead;
			}

			lastReadCount = fileInputStream.read(buffer, 0, maximumLengthToRead);
			bytesRead += lastReadCount;
			for (int i = 0; i < lastReadCount; i++) {
			    out.put(buffer[i] & 0xff);
			}
		    }
		}
		out.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    private final FileInputStream fileInputStream;
    private final FileChannel fileChannel;
    private final long start;
    private final int size;

    public FileData(File file, long start, int size) throws FileNotFoundException {
	this.fileInputStream = new FileInputStream(file);
	this.fileChannel = fileInputStream.getChannel();
	this.start = start;
	this.size = size;
    }

    @Override
    public byte[] getArray(int from, int length) {
	byte[] res = new byte[length];
	try {
	    synchronized (this) {
		int bytesRead = 0;
		fileChannel.position(start + from);

		while (bytesRead < length) {
		    bytesRead += fileInputStream.read(res, bytesRead, length - bytesRead);
		}
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
		res = fileInputStream.read();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return res;
    }
}
