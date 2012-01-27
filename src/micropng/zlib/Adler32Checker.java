package micropng.zlib;

import java.util.zip.Adler32;

import micropng.commonlib.StreamFilter;

public class Adler32Checker extends StreamFilter {

    private class Adler32CheckerThread implements Runnable {

	@Override
	public void run() {
	    int next = in();
	    while (next != -1) {
		adler32.update(next);
		out(next);
		next = in();
	    }
	    done();
	    zlibDecoder.readAndCompareAdler32CheckSum();
	}
    }

    private ZlibDecoder zlibDecoder;
    private Adler32 adler32;

    public Adler32Checker(ZlibDecoder zlibDecoder) {
	this.zlibDecoder = zlibDecoder;
	adler32 = new Adler32();
    }

    public void start() {
	new Thread(new Adler32CheckerThread()).start();
    }

    public boolean check(long checksum) {
	return adler32.getValue() == checksum;
    }
}
