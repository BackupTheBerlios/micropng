package micropng.zlib.deflate;

import micropng.commonlib.Queue;
import micropng.commonlib.RingBuffer;
import micropng.commonlib.StreamFilter;
import micropng.zlib.deflate.HuffmanTree.HuffmanTreeWalker;

public class HuffmanStreamDecoder extends StreamFilter {

    private final static int MAXIMUM_DISTANCE = 32768;

    private final static int[] lengthsTable = { 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 19, 23,
	    27, 31, 35, 43, 51, 59, 67, 83, 99, 115, 131, 163, 195, 227, 258 };

    private final static int[] extraBitsForLengthsTable = { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2,
	    2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 0 };

    private final static int[] distancesTable = { 1, 2, 3, 4, 5, 7, 9, 13, 17, 25, 33, 49, 65, 97,
	    129, 193, 257, 385, 513, 769, 1025, 1537, 2049, 3073, 4097, 6145, 8193, 12289, 16385,
	    24577 };

    private final static int[] extraBitsForDistancesTable = { 0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4,
	    5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13 };

    private HuffmanTree literalsAndLengths;
    private HuffmanTree distances;
    RingBuffer outputBuffer;

    public HuffmanStreamDecoder(HuffmanTree literalsAndLengths, HuffmanTree distances) {
	this.literalsAndLengths = literalsAndLengths;
	this.distances = distances;
	outputBuffer = new RingBuffer(MAXIMUM_DISTANCE);
    }

    private int readValueFromTree(HuffmanTreeWalker treeWalker, Queue input) {
	treeWalker.reset();
	do {
	    treeWalker.step(input.takeBit());
	} while (!treeWalker.isLeaf());

	return treeWalker.getValue();
    }

    @Override
    public void connect(StreamFilter nextInChain) {
	outputBuffer.connect(nextInChain);
    }

    public void decode(Queue input) {
	HuffmanTreeWalker literalsAndLengthsTreeWalker = literalsAndLengths.getHuffmanTreeWalker();
	HuffmanTreeWalker distancesTreeWalker = distances.getHuffmanTreeWalker();
	int literalOrLengthCode;

	do {
	    literalOrLengthCode = readValueFromTree(literalsAndLengthsTreeWalker, input);

	    if (literalOrLengthCode < 256) {
		outputBuffer.out(literalOrLengthCode);
	    } else if (literalOrLengthCode > 256) {
		int lengthsTableIndex = literalOrLengthCode - 257;
		int length = lengthsTable[lengthsTableIndex];
		int numberOfLengthExtraBits = extraBitsForLengthsTable[lengthsTableIndex];
		int lengthExtraBits = input.takeBits(numberOfLengthExtraBits);

		int distanceCode = readValueFromTree(distancesTreeWalker, input);
		int distance = distancesTable[distanceCode];
		int numberOfDistanceExtraBits = extraBitsForDistancesTable[distanceCode];
		int distanceExtraBits = input.takeBits(numberOfDistanceExtraBits);

		length <<= numberOfLengthExtraBits;
		length |= lengthExtraBits;

		distance <<= numberOfDistanceExtraBits;
		distance |= distanceExtraBits;

		outputBuffer.repeat(distance, length);
	    }
	} while (literalOrLengthCode != 256);
    }
}
