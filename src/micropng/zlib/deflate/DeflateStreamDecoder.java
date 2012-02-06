package micropng.zlib.deflate;

import micropng.commonlib.Queue;
import micropng.commonlib.RingBuffer;
import micropng.commonlib.StreamFilter;

public class DeflateStreamDecoder extends StreamFilter {
    private class DeflateBlockHeader {
	private final Queue input;
	private final int BFINAL;
	private final int BTYPE;

	public DeflateBlockHeader() {
	    input = getInputQueue();
	    BFINAL = input.takeBit();
	    BTYPE = input.takeBits(2);
	}

	public boolean isLast() {
	    return BFINAL != 0;
	}

	public DataBlockHeader getDataBlockHeader() {
	    switch (BTYPE) {
	    case 0x00:
		return new UncompressedBlockHeader();
	    case 0x01:
		return new FixedHuffmanBlockHeader();
	    case 0x02:
		return new DynamicHuffmanBlockHeader();
	    }

	    return null;
	}
    }

    private final static int MAXIMUM_DISTANCE = 32768;
    private final RingBuffer outputBuffer = new RingBuffer(MAXIMUM_DISTANCE);

    public void decode() {
	DeflateBlockHeader currentDeflateBlockHeader;
	shareCurrentOutputChannel(outputBuffer);
	do {
	    DataBlockHeader currentDataBlockHeader;

	    currentDeflateBlockHeader = new DeflateBlockHeader();
	    currentDataBlockHeader = currentDeflateBlockHeader.getDataBlockHeader();
	    shareCurrentInputChannel(currentDataBlockHeader);
	    currentDataBlockHeader.decode(outputBuffer);
	} while (!currentDeflateBlockHeader.isLast());

	done();
    }
}
