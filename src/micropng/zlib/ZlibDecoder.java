package micropng.zlib;

import micropng.commonlib.StreamFilter;
import micropng.zlib.deflate.DeflateStreamDecoder;

public class ZlibDecoder extends StreamFilter {
    private class ZlibDecoderThread implements Runnable {

	@Override
	public void run() {
	    int CMF;
	    @SuppressWarnings("unused")
	    int CM;
	    @SuppressWarnings("unused")
	    int CINFO;
	    int FLG;
	    @SuppressWarnings("unused")
	    int FCHECK;
	    @SuppressWarnings("unused")
	    int FDICT;
	    @SuppressWarnings("unused")
	    int FLEVEL;

	    // specified in zlib, but prohibited by png spec:
	    // int DICTID;
	    CMF = in();
	    CM = CMF & 0x0f; // must be 8
	    CINFO = (CMF & 0xf0) >>> 4; // must be 7
	    FLG = in();
	    FCHECK = FLG & 0x1f;
	    FDICT = (FLG & 0x20) >>> 5; // must be 0
	    FLEVEL = (FLG & 0xc0) >>> 6;

	    // specified in zlib, but prohibited by png spec:
	    // if (FDICT == 1) {
	    // DICTID = in();
	    // for (int i = 0; i < 3; i++) {
	    // DICTID <<= 8;
	    // DICTID |= in();
	    // }
	    // }

	    deflateDecoder.decode();
	}
    }

    private DeflateStreamDecoder deflateDecoder;
    private Adler32Checker adler32Checker;
    private ZlibDecoderThread zlibDecoderThread;
    private int adler32;

    public ZlibDecoder() {
	adler32Checker = new Adler32Checker(this);
    }

    public void readAndCompareAdler32CheckSum() {
	// TODO: maybe it is better to process this in a separate thread,
	// so the Adler32Checker thread is able to die?
	adler32 = 0;
	for (int i = 0; i < 4; i++) {
	    adler32 <<= 8;
	    adler32 |= in();
	}
	System.err.println("Adler-32 checksum is valid: " + adler32Checker.check(adler32));
    }

    public void start() {
	deflateDecoder = new DeflateStreamDecoder(getInputQueue());
	deflateDecoder.connect(adler32Checker);
	adler32Checker.start();

	zlibDecoderThread = new ZlibDecoderThread();

	new Thread(zlibDecoderThread).start();
    }

    @Override
    public void connect(StreamFilter nextInChain) {
	adler32Checker.connect(nextInChain);
    }
}
