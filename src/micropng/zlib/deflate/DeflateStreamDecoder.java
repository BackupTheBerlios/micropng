package micropng.zlib.deflate;

import micropng.commonlib.Queue;
import micropng.commonlib.StreamFilter;

public class DeflateStreamDecoder extends StreamFilter {
    private class DeflateBlockHeader {
	private Queue input;
	private int BFINAL;
	private int BTYPE;

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

    public void decode() {
	DeflateBlockHeader currentDeflateBlockHeader;
	DataBlockHeader currentDataBlockHeader;
	do {
	    currentDeflateBlockHeader = new DeflateBlockHeader();
	    currentDataBlockHeader = currentDeflateBlockHeader.getDataBlockHeader();
	    shareCurrentInputChannel(currentDataBlockHeader);
	    shareCurrentOutputChannel(currentDataBlockHeader);
	    currentDataBlockHeader.decode();
	} while (!currentDeflateBlockHeader.isLast());

	done();
    }
}
