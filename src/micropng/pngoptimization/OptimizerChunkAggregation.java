package micropng.pngoptimization;

import java.util.ArrayList;

import micropng.ChunkSequence;
import micropng.chunk.Chunk;
import micropng.chunk.Data;
import micropng.chunk.DataGroup;
import micropng.chunk.OrganisationSequence;
import micropng.chunk.OrganisationUnit;
import micropng.chunk.Type;

public class OptimizerChunkAggregation {

    public void optimize(OrganisationSequence organisationSequence) {
	for (OrganisationUnit u : organisationSequence) {
	    if (Type.IDAT.equals(u.getType())) {
		ChunkSequence chunkSequence = u.getChunks();
		if (chunkSequence.size() > 1) {
		    ArrayList<Data> currentList;
		    for (Chunk c : chunkSequence) {
			DataGroup g = new DataGroup();
		    }
		}
	    }
	}
    }
}
