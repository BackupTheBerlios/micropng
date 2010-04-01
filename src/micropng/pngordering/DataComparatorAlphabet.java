package micropng.pngordering;

import micropng.chunk.Chunk;

public class DataComparatorAlphabet implements DataComparator {

    @Override
    public int compare(OrderingKey a, OrderingKey b) {
	Chunk chunkA = a.getChunk();
	Chunk chunkB = b.getChunk();
	int minLength = Math.min(chunkA.getDataSize(), chunkB.getDataSize());
	int pos = 0;
	while (pos < minLength) {
	    if (chunkA.getByteAt(pos) < chunkB.getByteAt(pos)) {
		return -1;
	    }
	    if (chunkA.getByteAt(pos) > chunkB.getByteAt(pos)) {
		return 1;
	    }
	    pos++;
	}
	if (chunkA.getDataSize() < chunkB.getDataSize()) {
	    return -1;
	}
	if (chunkA.getDataSize() > chunkB.getDataSize()) {
	    return 1;
	}
	return 0;
    }
}
