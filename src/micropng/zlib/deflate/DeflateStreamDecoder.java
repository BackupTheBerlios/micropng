package micropng.zlib.deflate;

import micropng.commonlib.Queue;
import micropng.commonlib.StreamFilter;

public class DeflateStreamDecoder extends StreamFilter {
    private class DeflateBlockHeader {
	private int BFINAL;
	private int BTYPE;

	public DeflateBlockHeader() {
	    BFINAL = input.takeBit();
	    BTYPE = input.takeBits(2);
	}

	public boolean isLast() {
	    return BFINAL != 0;
	}

	public DataBlockHeader getDataBlockHeader() {
	    switch (BTYPE) {
	    case 0x00:
		return new UncompressedBlockHeader(input);
	    case 0x01:
		return new FixedHuffmanBlockHeader(input);
	    case 0x02:
		return new DynamicHuffmanBlockHeader(input);
	    }

	    return null;
	}
    }

    private Queue input;
    private StreamFilter nextInChain;

    public void decode() {
	this.input = getInputQueue();
	DeflateBlockHeader currentDeflateBlockHeader;
	DataBlockHeader currentDataBlockHeader;
	do {
	    currentDeflateBlockHeader = new DeflateBlockHeader();
	    currentDataBlockHeader = currentDeflateBlockHeader.getDataBlockHeader();
	    currentDataBlockHeader.connect(nextInChain);
	    currentDataBlockHeader.decode();
	} while (!currentDeflateBlockHeader.isLast());

	done();
    }

    @Override
    public void connect(StreamFilter nextInChain) {
	super.connect(nextInChain);
	this.nextInChain = nextInChain;
    }
}
