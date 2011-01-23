package micropng.chunkview.chunk;

import java.util.ArrayList;
import java.util.Iterator;

import micropng.chunkview.ChunkSequence;

@SuppressWarnings("serial")
public class OrganisationSequence extends ArrayList<OrganisationUnit> {

    public OrganisationSequence(ChunkSequence inputSequence) {
	Iterator<Chunk> inputSequenceIterator = inputSequence.iterator();
	Type lastMandatory = null;
	ChunkSequence currentSequence = null;

	while (inputSequenceIterator.hasNext()) {
	    Chunk currentChunk = inputSequenceIterator.next();
	    int currentType = currentChunk.getType();

	    if (!((Type.IDAT.equals(currentType)) && (Type.IDAT.equals(lastMandatory)))) {
		currentSequence = new ChunkSequence();
		currentSequence.add(currentChunk);
		add(new OrganisationUnit(currentSequence, lastMandatory));
	    } else {
		currentSequence.add(currentChunk);
	    }

	    if (!Type.isAncillary(currentType)) {
		lastMandatory = Type.valueOf(currentType);
	    }
	}
    }

    public ChunkSequence toChunkSequence() {
	ChunkSequence res = new ChunkSequence();
	for (OrganisationUnit u : this) {
	    res.addAll(u.getChunks());
	}
	return res;
    }
}