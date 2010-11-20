package micropng;

public class DeflateDecoder {
    private class DeflateDecoderThread implements Runnable {
	private class HuffmanTree {

	    private class HuffmanNode {

		private int value;
		private HuffmanNode left;
		private HuffmanNode right;

		public HuffmanNode() {

		}

		public int getValue() {
		    return value;
		}

		public void setValue(int value) {
		    this.value = value;
		}

		public HuffmanNode getLeft() {
		    return left;
		}

		public void setLeft(HuffmanNode left) {
		    this.left = left;
		}

		public HuffmanNode getRight() {
		    return right;
		}

		public void setRight(HuffmanNode right) {
		    this.right = right;
		}

	    }

	    private static final int MAX_BITS = 15;

	    private HuffmanNode root;

	    public HuffmanTree(int[] codeLengths) {
		root = new HuffmanNode();
		int[] bl_count = new int[MAX_BITS + 1];
		int[] next_code = new int[MAX_BITS + 1];
		int code = 0;

		for (int i : codeLengths) {
		    bl_count[i]++;
		}

		bl_count[0] = 0;

		for (int bits = 1; bits <= MAX_BITS; bits++) {
		    code = (code + bl_count[bits - 1]) << 1;
		    next_code[bits] = code;
		}

		for (int n = 0; n <= codeLengths.length; n++) {
		    int len = codeLengths[n];
		    if (len != 0) {
			addCode(n, next_code[len], len);
			next_code[len]++;
		    }
		}
	    }

	    private void addCode(int value, int code, int length) {
		HuffmanNode currentNode = root;
		int mask = 0x01 << length;
		for (int i = 0; i < length; i++) {
		    HuffmanNode nextNode;
		    boolean leftTree;
		    mask >>= 1;
		    leftTree = ((mask & code) == 0);

		    if (leftTree) {
			nextNode = currentNode.getLeft();
			if (nextNode == null) {
			    nextNode = new HuffmanNode();
			    currentNode.setLeft(nextNode);
			}
		    } else {
			nextNode = currentNode.getRight();
			if (nextNode == null) {
			    nextNode = new HuffmanNode();
			    currentNode.setRight(nextNode);
			}
		    }
		    currentNode = nextNode;
		}
		currentNode.setValue(value);
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
	    int HLIT = getNextBits(5);
	    int HDIST = getNextBits(5);
	    int HCLEN = getNextBits(4);
	    int numberOfLiteralAndLengthCodes = HLIT + 257;
	    int numberOfDistanceCodes = HDIST + 1;
	    int numberOfCodeLengthCodes = HCLEN + 4;
	    int[] codeLengthCodes = new int[19];
	    int[] codeLengthCodesOrder = { 16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2,
		    14, 1, 15 };
	    int numberOfLiteralAndLengthCodesRead = 0;
	    int numberOfDistanceCodesRead = 0;

	    for (int i = 0; i < numberOfCodeLengthCodes; i++) {
		codeLengthCodes[codeLengthCodesOrder[i]] = getNextBits(3);
	    }

	    /*while (numberOfLiteralAndLengthCodesRead < numberOfLiteralAndLengthCodes) {
		numberOfLiteralAndLengthCodesRead++; //TODO
	    }

	    while (numberOfDistanceCodesRead < numberOfDistanceCodes) {
		numberOfDistanceCodesRead++; //TODO
	    }*/
	    //argh: The code length repeat codes can cross from HLIT + 257 to the
	    // HDIST + 1 code lengths.
    
	    // read codes while not value == 256
	    
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
