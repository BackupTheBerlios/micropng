package micropng.zlib;

import micropng.commonlib.Queue;

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
	    int CM;
	    int CINFO;
	    int FLG;
	    int FCHECK;
	    int FDICT;
	    int FLEVEL;
	    int next;

	    // specified in zlib, but prohibited by png spec:
	    // int DICTID;
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

		next = input.take();
		while (next != -1) {
		    output.put(next);
		    next = input.take();
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
