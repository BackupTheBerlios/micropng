package micropng.zlib.deflate;

import micropng.commonlib.Queue;
import micropng.commonlib.RingBuffer;
import micropng.commonlib.StreamFilter;
import micropng.zlib.deflate.HuffmanTree.HuffmanTreeWalker;

public class HuffmanStreamDecoder extends StreamFilter {

    private final static int[] lengthsTable = { 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 19, 23,
	    27, 31, 35, 43, 51, 59, 67, 83, 99, 115, 131, 163, 195, 227, 258 };

    private final static int[] extraBitsForLengthsTable = { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2,
	    2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 0 };

    private final static int[] distancesTable = { 1, 2, 3, 4, 5, 7, 9, 13, 17, 25, 33, 49, 65, 97,
	    129, 193, 257, 385, 513, 769, 1025, 1537, 2049, 3073, 4097, 6145, 8193, 12289, 16385,
	    24577 };

    private final static int[] extraBitsForDistancesTable = { 0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4,
	    5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13 };

    private final HuffmanTree literalsAndLengths;
    private final HuffmanTree distances;
    private Queue input; // TODO: this should be final

    public HuffmanStreamDecoder(HuffmanTree literalsAndLengths, HuffmanTree distances) {
	this.literalsAndLengths = literalsAndLengths;
	this.distances = distances;
    }

    private int readValueFromTree(HuffmanTreeWalker treeWalker) {
	treeWalker.reset();
	do {
	    treeWalker.step(input.takeBit());
	} while (!treeWalker.isLeaf());

	return treeWalker.getValue();
    }

    public void decode(RingBuffer outputBuffer) {
	final HuffmanTreeWalker literalsAndLengthsTreeWalker;
	final HuffmanTreeWalker distancesTreeWalker;
	int literalOrLengthCode;

	input = getInputQueue();
	literalsAndLengthsTreeWalker = literalsAndLengths.getHuffmanTreeWalker();
	distancesTreeWalker = distances.getHuffmanTreeWalker();

	do {
	    literalOrLengthCode = readValueFromTree(literalsAndLengthsTreeWalker);

	    if (literalOrLengthCode < 256) {
		outputBuffer.put(literalOrLengthCode);
	    } else if (literalOrLengthCode > 256) {
		final int lengthsTableIndex = literalOrLengthCode - 257;
		final int length;
		final int numberOfLengthExtraBits = extraBitsForLengthsTable[lengthsTableIndex];
		final int lengthExtraBits = input.takeBits(numberOfLengthExtraBits);

		final int distanceCode = readValueFromTree(distancesTreeWalker);
		final int distance;
		final int numberOfDistanceExtraBits = extraBitsForDistancesTable[distanceCode];
		final int distanceExtraBits = input.takeBits(numberOfDistanceExtraBits);

		length = lengthsTable[lengthsTableIndex] + lengthExtraBits;
		distance = distancesTable[distanceCode] + distanceExtraBits;

		outputBuffer.repeat(distance, length);
	    }
	} while (literalOrLengthCode != 256);
    }
}
