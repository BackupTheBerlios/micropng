package micropng.chunk;

public class ChunkBehaviour {

    private int chunkOrientation;
    private SameTypeComparator sameTypeComparator;

    public ChunkBehaviour(int chunkOrientation, SameTypeComparator sameTypeComparator) {
	this.chunkOrientation = chunkOrientation;
	this.sameTypeComparator = sameTypeComparator;
    }

    public int getLeadingMandatoryChunk() {
	return chunkOrientation;
    }

    public SameTypeComparator sameTypeComparator() {
	return sameTypeComparator;
    }
}
