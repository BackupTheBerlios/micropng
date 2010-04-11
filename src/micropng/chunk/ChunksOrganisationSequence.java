package micropng.chunk;

import java.util.ArrayList;
import java.util.Iterator;

import micropng.ChunkSequence;

public class ChunksOrganisationSequence implements Iterable<ChunksOrganisationUnit> {

    private ArrayList<ChunksOrganisationUnit> chunkList;

    public ChunksOrganisationSequence(ChunkSequence inputSequence) {
	Iterator<Chunk> inputSequenceIterator = inputSequence.iterator();
	int lastMandatory = 0;
	ChunkSequence currentSequence = null;

	chunkList = new ArrayList<ChunksOrganisationUnit>();

	while (inputSequenceIterator.hasNext()) {
	    Chunk currentChunk = inputSequenceIterator.next();
	    int currentType = currentChunk.getType();

	    if (!((ChunkType.IDAT.equals(currentType)) && (ChunkType.IDAT.equals(lastMandatory)))) {
		ChunkBehaviour currentBehaviour = ChunkBehaviourFactory.getChunkBehaviour(currentType, lastMandatory);
		currentSequence = new ChunkSequence();
		chunkList.add(new ChunksOrganisationUnit(currentSequence, currentBehaviour));
	    }

	    currentSequence.append(currentChunk);

	    if (ChunkType.isAncillary(currentType)) {
		lastMandatory = currentType;
	    }
	}
    }

    @Override
    public Iterator<ChunksOrganisationUnit> iterator() {
	return chunkList.iterator();
    }
}
