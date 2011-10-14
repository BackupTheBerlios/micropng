package micropng.zlib.deflate;

import micropng.commonlib.Queue;

public class DeflateStreamDecoder {
    private class DeflateDecoderThread implements Runnable {
	private Queue input;
	private Queue output;

	public DeflateDecoderThread(Queue input, Queue output) throws InterruptedException {
	    this.input = input;
	    this.output = output;
	}

	@Override
	public void run() {
	    try {
		DeflateBlockHeader currentHeader;
		do {
		    currentHeader = new DeflateBlockHeader(input);
		} while (!currentHeader.isLast());

		output.close();
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    public DeflateStreamDecoder() {
    }

    public void decompress(Queue input, Queue output) throws InterruptedException {
	new DeflateDecoderThread(input, output).run();
    }
}
