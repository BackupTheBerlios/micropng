package micropng.zlib;

import micropng.commonlib.Queue;
import micropng.zlib.deflate.DeflateStreamDecoder;

public class ZlibDecoder {
    private class ZlibDecoderThread implements Runnable {

	private Queue input, output;

	private ZlibDecoderThread(Queue input, Queue output) {
	    this.input = input;
	    this.output = output;
	}

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
	    DeflateStreamDecoder deflateDecoder = new DeflateStreamDecoder();
	    // specified in zlib, but prohibited by png spec:
	    // int DICTID;
	    int	ADLER32 = 0;
	    try {
		CMF = input.take();
		CM = CMF & 0x0f; // must be 8
		CINFO = (CMF & 0xf0) >>> 4; // must be 7
		FLG = input.take();
		FCHECK = FLG & 0x1f;
		FDICT = (FLG & 0x20) >>> 5; // must be 0
		FLEVEL = (FLG & 0xc0) >>> 6;

		// specified in zlib, but prohibited by png spec:
		// if (FDICT == 1) {
		// DICTID = input.take();
		// for (int i = 0; i < 3; i++) {
		// DICTID <<= 8;
		// DICTID |= input.take();
		// }
		// }

		deflateDecoder.decompress(input, output);

		for (int i = 0; i < 4; i++) {
		    ADLER32 <<=8;
		    ADLER32 |= input.take();
		}

		output.close();
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    public ZlibDecoder() {
    }

    public Queue decode(Queue input) {
	Queue res = new Queue();
	new ZlibDecoderThread(input, res).run();
	return res;
    }
}
