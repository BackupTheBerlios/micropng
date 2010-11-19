package micropng;

public class DeflateDecoder {
    private class DeflateDecoderThread implements Runnable {
	private class HuffmanTree {

	    int[] codeLengths;
	    int[] codeLengthFrequencies;
	    

	    public HuffmanTree () {
	    }
	}
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

	private int getNextBit() throws InterruptedException {
	    currentByte >>= 1;
	    pos++;

	    if (pos == 7) {
		readByte();
	    }

	    return currentByte & 0x01;
	}

	private int getNextBits(int numberOfBits) throws InterruptedException {
	    int res = 0;
	    for (int i = 0; i < numberOfBits; i++) {
		res <<= 1;
		res |= getNextBit();
	    }
	    return res;
	}

	private int getNextByte() throws InterruptedException {
	    int res = currentByte;
	    readByte();
	    return res;
	}

	private void readUncompressedBlock() throws InterruptedException {
	    int LEN;
	    int NLEN;
	    dropRemainingBitsOfCurrentByte();
	    LEN = getNextByte();
	    LEN <<= 8;
	    LEN |= getNextByte();
	    NLEN = getNextByte();
	    LEN <<= 8;
	    NLEN |= getNextByte();

	    for (int i = 0; i < LEN; i++) {
		output.put(input.take());
	    }
	}

	private void readFixedHuffmanBlock() throws InterruptedException {
	
	}

	private void readDynamicHuffmanBlock() throws InterruptedException {
	    int HLIT = getNextBits(5); // # of Literal/Length codes - 257 (257 - 286)
            int HDIST = getNextBits(5); // # of Distance codes - 1        (1 - 32)
            int HCLEN = getNextBits(4); // # of Code Length codes - 4     (4 - 19)
	}

	private boolean readBlock() throws InterruptedException {
	    boolean res;
	    int BFINAL;
	    int BTYPE = 0;

	    BFINAL = getNextBit();
	    res = BFINAL != 0;
	    BTYPE = getNextBit();
	    BTYPE <<= 1;
	    BTYPE |= getNextBit();

	    switch (BTYPE) {
	    case 0x00:
		readUncompressedBlock();
		break;
	    case 0x01:
		readFixedHuffmanBlock();
		break;
	    case 0x10:
		readDynamicHuffmanBlock();
		break;
	    }

	    return res;
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
