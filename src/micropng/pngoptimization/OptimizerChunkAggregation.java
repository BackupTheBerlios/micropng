package micropng.pngoptimization;

import micropng.ChunkSequence;
import micropng.chunk.Chunk;
import micropng.chunk.Type;

public class OptimizerChunkAggregation {

    public ChunkSequence optimize(ChunkSequence seq) {
	ChunkSequence res = new ChunkSequence();
	for (Chunk c : seq) {
	    if (Type.IDAT.equals(c.getType())) {
		
	    }
	}
	return res;
    }
}
