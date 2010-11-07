package micropng;

public class ZLibDecoder {
    private class ZLibDecoderThread implements Runnable {

	private Queue input, output;

	public ZLibDecoderThread(Queue input, Queue output) {
	    this.input = input;
	    this.output = output;
	}

	@Override
	public void run() {
	    int next;
	    int CMF;
	    int CM;
	    int CINFO;
	    int FLG;
	    int FCHECK;
	    int FDICT;
	    int FLEVEL;
	    int DICTID;
	    try {
		CMF = input.take();
		CM = CMF & 0x0f;
		CINFO = (CMF & 0xf0) >>> 4;
		FLG = input.take();
		FCHECK = FLG & 0x1f;
		FDICT = (FLG & 0x20) >>> 5;
		FLEVEL = (FLG & 0xc0) >>> 6;

		if (FDICT == 1) {
		    DICTID = input.take();
		    for (int i = 0; i < 3; i++) {
			DICTID <<= 8;
			DICTID |= input.take();
		    }
		}

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

    public ZLibDecoder() {
    }

    public Queue Decompress(Queue input) throws InterruptedException {
	Queue res = new Queue();
	new ZLibDecoderThread(input, res).run();
	return res;
    }
}
