package micropng.zlib.deflate;

import micropng.commonlib.BitQueue;
import micropng.commonlib.Queue;

public class DeflateBlockHeader {
    private int BFINAL;
    private int BTYPE;
    private BitQueue input;

    public DeflateBlockHeader(Queue stream) throws InterruptedException {
	input = new BitQueue(stream);

	BFINAL = input.take();
	BTYPE = input.take();
	BTYPE <<= 1;
	BTYPE |= input.take();
    }

    public boolean isLast() {
	return BFINAL != 0;
    }

    public DataBlockHeader getDataBlockHeader(BitQueue input) throws InterruptedException {
	switch (BTYPE) {
	case 0x00:
	    return new UncompressedBlockHeader(input);
	case 0x01:
	    return new FixedHuffmanBlockHeader(input);
	case 0x10:
	    return new DynamicHuffmanBlockHeader(input);
	}

	return null;
    }
}
