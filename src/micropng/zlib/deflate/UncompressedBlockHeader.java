package micropng.zlib.deflate;

import java.util.ArrayList;

import micropng.commonlib.BitArrayListConverter;
import micropng.commonlib.Queue;
import micropng.commonlib.RingBuffer;

public class UncompressedBlockHeader extends DataBlockHeader {

    private Queue input;
    private int length;
    private ArrayList<Integer> originalHeaderBits;
    private static final int bitsForLEN = 16;
    private int sizeOfHeaderPadding;

    private int readAndStore(int numberOfBits) {
	int res = input.takeBits(numberOfBits);
	BitArrayListConverter.append(res, originalHeaderBits, numberOfBits);
	return res;
    }

    @Override
    public void decode(RingBuffer output) {
	int LEN;
	@SuppressWarnings("unused")
	int NLEN;
	input = getInputQueue();
	originalHeaderBits = input.getRemainingBitsOfCurrentByte();
	sizeOfHeaderPadding = originalHeaderBits.size();
	LEN = readAndStore(bitsForLEN);
	NLEN = readAndStore(bitsForLEN);
	length = LEN;

	for (int i = 0; i < length; i++) {
	    output.out(input.take());
	}
    }

    @Override
    public ArrayList<Integer> getOriginalHeader() {
	return originalHeaderBits;
    }

    public int getSizeOfHeaderPadding() {
	return sizeOfHeaderPadding;
    }
}
