package micropng.contentview;

import micropng.chunkview.chunk.Chunk;

public class SignificantBits {

    private byte[] significanceTable;

    public SignificantBits(Chunk sBITChunk) {
	significanceTable = sBITChunk.getData().getArray();
    }

    public int significantBitsInChannel(int channel) {
	return significanceTable[channel];
    }
}
