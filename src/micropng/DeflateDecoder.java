package micropng;

public class DeflateDecoder {
    private class DeflateDecoderThread implements Runnable {

	private Queue input;
	private Queue output;
	private int pos;
	private int currentByte;

	public DeflateDecoderThread(Queue input, Queue output) throws InterruptedException {
	    this.input = input;
	    this.output = output;
	    this.pos = 0;
	    this.currentByte = input.take();
	    
	}

	private int readValue() {
	    return 0;
	}

	private void readHuffmanTrees() {
	    
	}

	/* 
	 * step to the next input byte
	 */
	private void readByte() throws InterruptedException {
	    pos = 0;
	    currentByte = input.take();
	}

	/*
	 * if the pointer is not at a byte boundary, jump to the next one
	 */
	private void dropRemainingBitsOfCurrentByte() throws InterruptedException {
	    if (pos != 0) {
		readByte();
	    }
	}

	private int getNextBit () throws InterruptedException {
	    currentByte >>= 1;
	    pos++;

	    if (pos == 7) {
		readByte();
	    }

	    return currentByte & 0x01;
	}

	private boolean readBlock() throws InterruptedException {
	    int BFINAL;
	    int BTYPE = 0;

	    BFINAL = getNextBit();
	    BTYPE = getNextBit();
	    BTYPE <<= 1;
	    BTYPE |= getNextBit();

	    return true;
	}

	@Override
	public void run() {
	    try {
		boolean lastBlockRead = false;
		while (!lastBlockRead) {
		    lastBlockRead = readBlock();
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
