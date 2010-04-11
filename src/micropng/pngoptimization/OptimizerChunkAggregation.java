package micropng.pngoptimization;

import micropng.ChunkSequence;
import micropng.chunk.Chunk;
import micropng.chunk.ChunkType;

public class OptimizerChunkAggregation {

    public ChunkSequence optimize(ChunkSequence seq) {
	ChunkSequence res = new ChunkSequence();
	for (Chunk c : seq) {
	    if (ChunkType.IDAT.equals(c.getType())) {
		
	    }
	}
	return res;
    }
}
