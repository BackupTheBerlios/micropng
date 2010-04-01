package micropng.pngoptimization;

import micropng.ChunkSequence;
import micropng.chunk.Chunk;
import micropng.chunk.IDAT;

public class OptimizerChunkAggregation {

    public ChunkSequence optimize(ChunkSequence seq) {
	ChunkSequence res = new ChunkSequence();
	for (Chunk c : seq) {
	    if (c.isType(IDAT.getType())) {
		
	    }
	}
	return res;
    }
}
