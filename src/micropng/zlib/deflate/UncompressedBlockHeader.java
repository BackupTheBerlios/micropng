package micropng.zlib.deflate;

import java.util.ArrayList;

import micropng.commonlib.BitArrayListConverter;
import micropng.commonlib.BitQueue;
import micropng.commonlib.Queue;

public class UncompressedBlockHeader implements DataBlockHeader {

    private BitQueue input;
    private int length;
    private ArrayList<Integer> originalHeaderBits;
    private static final int bitsForLEN = 16;
    private int sizeOfHeaderPadding;

    public UncompressedBlockHeader(BitQueue input) throws InterruptedException {
	int LEN;
	@SuppressWarnings("unused")
	int NLEN;
	this.input = input;
	originalHeaderBits = input.getRemainingBitsOfCurrentByte();
	sizeOfHeaderPadding = originalHeaderBits.size();
	LEN = readAndStore(bitsForLEN);
	NLEN = readAndStore(bitsForLEN);
	length = LEN;
    }

    private int readAndStore(int numberOfBits) throws InterruptedException {
	int res = input.take(numberOfBits);
	BitArrayListConverter.append(res, originalHeaderBits, numberOfBits);
	return res;
    }
    
    public void decode(Queue output) throws InterruptedException {
	for (int i = 0; i < length; i++) {
	    output.put(input.takeByte());
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
