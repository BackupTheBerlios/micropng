package micropng.chunk;

import java.io.IOException;
import java.io.RandomAccessFile;

import micropng.MicroPNGQueue;

public class FileData implements Data {

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
	    file.read(res, from, to-from);
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
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public MicroPNGQueue getStream() {
	return getStream(0, end - start);
    }
}
