package micropng.chunkview.chunk;

import java.util.ArrayList;

import micropng.chunkview.ChunkSequence;

@SuppressWarnings("serial")
public class OrganisationSequence extends ArrayList<OrganisationUnit> {

    public OrganisationSequence(ChunkSequence inputSequence) {
	Type lastMandatory = null;
	ChunkSequence currentSequence = null;

	for (Chunk currentChunk : inputSequence) {
	    int currentType = currentChunk.getType();

	    if (!((Type.IDAT.toInt() == currentType) && (Type.IDAT.equals(lastMandatory)))) {
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
