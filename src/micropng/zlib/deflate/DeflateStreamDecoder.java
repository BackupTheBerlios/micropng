package micropng.zlib.deflate;

import micropng.commonlib.Queue;
import micropng.commonlib.StreamFilter;
import micropng.commonlib.StreamsMerger;

public class DeflateStreamDecoder extends StreamFilter {
    private class DeflateBlockHeader {
	private int BFINAL;
	private int BTYPE;

	public DeflateBlockHeader() throws InterruptedException {
	    BFINAL = input.takeBit();
	    BTYPE = input.takeBits(2);
	}

	public boolean isLast() {
	    return BFINAL != 0;
	}

	public DataBlockHeader getDataBlockHeader() throws InterruptedException {
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

    private class DeflateDecoderThread implements Runnable {

	@Override
	public void run() {
	    try {
		DeflateBlockHeader currentDeflateBlockHeader;
		DataBlockHeader currentDataBlockHeader;
		do {
		    currentDeflateBlockHeader = new DeflateBlockHeader();
		    currentDataBlockHeader = currentDeflateBlockHeader.getDataBlockHeader();
		    merger.add(currentDataBlockHeader);
		    currentDataBlockHeader.decode();
		} while (!currentDeflateBlockHeader.isLast());

		done();
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    private Queue input;
    private StreamsMerger merger;

    public DeflateStreamDecoder(Queue inputQueue) {
	this.input = inputQueue;
	merger = new StreamsMerger();
    }

    public void decompress() throws InterruptedException {
	new DeflateDecoderThread().run();
    }

    @Override
    public void connect(StreamFilter nextInChain) {
	merger.connect(nextInChain);
    }
}
