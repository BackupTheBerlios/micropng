package micropng.chunk;

import micropng.ChunkSequence;

public class ChunksOrganisationUnit {

    private ChunkSequence chunks;
    private ChunkBehaviour behaviour;

    public ChunksOrganisationUnit(ChunkSequence chunks, ChunkBehaviour behaviour) {
	this.chunks = chunks;
	this.behaviour = behaviour;
    }

}
