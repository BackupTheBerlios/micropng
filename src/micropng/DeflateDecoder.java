package micropng;

public class DeflateDecoder {
    private class DeflateDecoderThread implements Runnable {

	private Queue input, output;

	public DeflateDecoderThread(Queue input, Queue output) {
	    this.input = input;
	    this.output = output;
	}

	@Override
	public void run() {
	    int next;
	    try {
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

    public DeflateDecoder() {
    }

    public Queue Decompress(Queue input) throws InterruptedException {
	Queue res = new Queue();
	new DeflateDecoderThread(input, res).run();
	return res;
    }
}
