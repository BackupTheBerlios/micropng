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
	    int ADLER32 = 0;
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

	    deflateDecoder.start();

	    for (int i = 0; i < 4; i++) {
		ADLER32 <<= 8;
		ADLER32 |= in();
	    }
	}
    }

    private DeflateStreamDecoder deflateDecoder;
    private ZlibDecoderThread zlibDecoderThread;

    public ZlibDecoder() {
	deflateDecoder = new DeflateStreamDecoder();
	zlibDecoderThread = new ZlibDecoderThread();
    }

    public void start() {
	new Thread(zlibDecoderThread).start();
    }
}
