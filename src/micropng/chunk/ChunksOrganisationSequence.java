package micropng.chunk;

import java.util.ArrayList;
import java.util.Iterator;

import micropng.ChunkSequence;

public class ChunksOrganisationSequence implements Iterable<ChunksOrganisationUnit> {

    private ArrayList<ChunksOrganisationUnit> chunkUnitList;

    public ChunksOrganisationSequence(ChunkSequence inputSequence) {
	Iterator<Chunk> inputSequenceIterator = inputSequence.iterator();
	int lastMandatory = 0;
	ChunkSequence currentSequence = null;

	chunkUnitList = new ArrayList<ChunksOrganisationUnit>();

	while (inputSequenceIterator.hasNext()) {
	    Chunk currentChunk = inputSequenceIterator.next();
	    int currentType = currentChunk.getType();

	    if (!((ChunkType.IDAT.equals(currentType)) && (ChunkType.IDAT.equals(lastMandatory)))) {
		ChunkBehaviour currentBehaviour = ChunkBehaviourFactory.getChunkBehaviour(currentType, lastMandatory);
		currentSequence = new ChunkSequence();
		chunkUnitList.add(new ChunksOrganisationUnit(currentSequence, currentBehaviour));
	    }

	    currentSequence.add(currentChunk);

	    if (ChunkType.isAncillary(currentType)) {
		lastMandatory = currentType;
	    }
	}
    }

    @Override
    public Iterator<ChunksOrganisationUnit> iterator() {
	return chunkUnitList.iterator();
    }

    public ChunkSequence toChunkSequence() {
	ChunkSequence res = new ChunkSequence();
	for (ChunksOrganisationUnit u : chunkUnitList) {
	    res.addAll(u.getChunks());
	}
	return res;
    }
}
