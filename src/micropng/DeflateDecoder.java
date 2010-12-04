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
	    int HLIT = getNextBits(5);
	    int HDIST = getNextBits(5);
	    int HCLEN = getNextBits(4);
	    int numberOfLiteralAndLengthCodes = HLIT + 257;
	    int numberOfDistanceCodes = HDIST + 1;
	    int numberOfCodeLengthCodes = HCLEN + 4;
	    int[] codeLengthCodes = new int[numberOfCodeLengthCodes];
	    int[] codeLengthCodesOrder = { 16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2,
		    14, 1, 15 };
	    HuffmanTree codeLengthsTree;
	    int[] literalLengthAndDistanceCodesTable = new int[numberOfLiteralAndLengthCodes + numberOfDistanceCodes];
	    int posInTable = 0;

	    for (int i = 0; i < numberOfCodeLengthCodes; i++) {
		codeLengthCodes[codeLengthCodesOrder[i]] = getNextBits(3);
	    }

	    codeLengthsTree = new HuffmanTree(codeLengthCodes);

	    while (posInTable < literalLengthAndDistanceCodesTable.length) {
		
	    }

	    // read codes while not value == 256
	    
	}

	private boolean readDeflateBlock() throws InterruptedException {
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
		    lastBlockRead = readDeflateBlock();
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
