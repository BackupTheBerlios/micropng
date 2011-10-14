package micropng.zlib.deflate;

import java.util.ArrayList;

import micropng.commonlib.BitArrayListConverter;
import micropng.commonlib.BitQueue;
import micropng.commonlib.Queue;
import micropng.zlib.deflate.HuffmanTree.HuffmanTreeWalker;

public class DynamicHuffmanBlockHeader implements DataBlockHeader {

    private BitQueue input;
    private int HLIT;
    private int HDIST;
    private int HCLEN;
    private HuffmanTree literalsAndLengthsTree;
    private HuffmanTree distancesTree;
    private ArrayList<Integer> originalHeaderBits;

    public DynamicHuffmanBlockHeader(BitQueue input) throws InterruptedException {
	this.input = input;
	originalHeaderBits = new ArrayList<Integer>(256);
	HLIT = readAndStore(5);
	HDIST = readAndStore(5);
	HCLEN = readAndStore(4);

	int numberOfLiteralAndLengthCodes = HLIT + 257;
	int numberOfDistanceCodes = HDIST + 1;
	int numberOfCodeLengthCodes = HCLEN + 4;
	int[] codeLengthCodes = new int[numberOfCodeLengthCodes];
	int[] codeLengthCodesOrder = { 16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14,
		1, 15 };
	HuffmanTree codeLengthsTree;
	HuffmanTreeWalker codeLengthsTreeWalker;
	int[] literalLengthsAndDistanceCodesTable = new int[numberOfLiteralAndLengthCodes
		+ numberOfDistanceCodes];
	int[] literalAndLengthCodesTable = new int[numberOfLiteralAndLengthCodes];
	int[] distanceCodesTable = new int[numberOfDistanceCodes];
	int codesTableIndex = 0;

	for (int i = 0; i < numberOfCodeLengthCodes; i++) {
	    codeLengthCodes[codeLengthCodesOrder[i]] = readAndStore(3);
	}

	codeLengthsTree = new HuffmanTree(codeLengthCodes);
	codeLengthsTreeWalker = codeLengthsTree.getHuffmanTreeWalker();

	while (codesTableIndex < literalLengthsAndDistanceCodesTable.length) {
	    int codeLength;
	    int repeat;

	    codeLengthsTreeWalker.reset();
	    while (!codeLengthsTreeWalker.isLeaf()) {
		int nextBit = readAndStore(1);
		codeLengthsTreeWalker.step(nextBit);
	    }
	    codeLength = codeLengthsTreeWalker.getValue();

	    switch (codeLength) {
	    case 16:
		repeat = 3 + readAndStore(2);

		for (int i = 0; i < repeat; i++) {
		    literalLengthsAndDistanceCodesTable[codesTableIndex] = literalLengthsAndDistanceCodesTable[codesTableIndex - 1];
		    codesTableIndex++;
		}
		break;
	    case 17:
		repeat = 3 + readAndStore(3);

		for (int i = 0; i < repeat; i++) {
		    literalLengthsAndDistanceCodesTable[codesTableIndex] = 0;
		    codesTableIndex++;
		}
		break;
	    case 18:
		repeat = 11 + readAndStore(7);

		for (int i = 0; i < repeat; i++) {
		    literalLengthsAndDistanceCodesTable[codesTableIndex] = 0;
		    codesTableIndex++;
		}
		break;
	    default:
		literalLengthsAndDistanceCodesTable[codesTableIndex] = codeLength;
		codesTableIndex++;
		break;
	    }
	}

	System.arraycopy(literalLengthsAndDistanceCodesTable, 0, literalAndLengthCodesTable, 0,
		numberOfLiteralAndLengthCodes);
	System.arraycopy(literalLengthsAndDistanceCodesTable, 0, distanceCodesTable,
		numberOfLiteralAndLengthCodes, numberOfDistanceCodes);

	literalsAndLengthsTree = new HuffmanTree(literalAndLengthCodesTable);
	distancesTree = new HuffmanTree(distanceCodesTable);
    }

    private int readAndStore(int numberOfBits) throws InterruptedException {
	int res = input.take(numberOfBits);
	BitArrayListConverter.append(res, originalHeaderBits, numberOfBits);
	return res;
    }

    @Override
    public void decode(Queue output) throws InterruptedException {
	HuffmanStreamDecoder decoder;
	decoder = new HuffmanStreamDecoder(literalsAndLengthsTree, distancesTree);
	decoder.decode(input, output);
    }

    @Override
    public ArrayList<Integer> getOriginalHeader() {
	return originalHeaderBits;
    }
}
