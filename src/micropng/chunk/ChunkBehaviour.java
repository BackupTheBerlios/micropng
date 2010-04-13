package micropng.chunk;

public class ChunkBehaviour {

    private ChunkType chunkOrientation;
    private SameTypeComparator sameTypeComparator;

    public ChunkBehaviour(ChunkType chunkOrientation, SameTypeComparator sameTypeComparator) {
	this.chunkOrientation = chunkOrientation;
	this.sameTypeComparator = sameTypeComparator;
    }

    public ChunkType getLeadingMandatoryChunk() {
	return chunkOrientation;
    }

    public SameTypeComparator sameTypeComparator() {
	return sameTypeComparator;
    }
}
