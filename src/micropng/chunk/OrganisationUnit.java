package micropng.chunk;

import java.util.Iterator;

import micropng.ChunkSequence;

public class OrganisationUnit implements Comparable<OrganisationUnit> {

    private ChunkSequence chunks;
    private int type;
    private Type previousType;

    /**
     * 
     * @param chunks
     *            non-null ChunkSequence with at least one element
     * @param behaviour
     *            non-null ChunkBehaviour
     * @param previousType
     */

    public OrganisationUnit(ChunkSequence chunks, Type previousType) {
	this.chunks = chunks;
	this.type = chunks.elementAt(0).getType();

	if (Type.isKnown(type)) {
	    this.previousType = Orientation.valueOf(type).getOrientation();
	} else {
	    if (Type.isSafeToCopy(type)) {
		this.previousType = (Orientation.beforeIDAT(previousType)) ? Type.IDAT
			: Type.IHDR;
	    } else {
		this.previousType = previousType;
	    }
	}
    }

    public ChunkSequence getChunks() {
	return chunks;
    }

    public int getByteAt(int pos) {
	Iterator<Chunk> chunksIterator = chunks.iterator();

	int currentPos = 0;
	Chunk currentChunk = chunksIterator.next();
	int currentChunkSize = currentChunk.getDataSize();

	while (currentPos + currentChunkSize < pos) {
	    currentPos += currentChunkSize;
	    currentChunk = chunksIterator.next();
	    currentChunkSize = currentChunk.getDataSize();
	}

	return currentChunk.getByteAt(pos - currentPos);
    }

    public int getDataSize() {
	int res = 0;
	for (Chunk c : chunks) {
	    res += c.getDataSize();
	}
	return res;
    }

    private int compareOrientation(OrganisationUnit c) {
	int sizeOfOrientationOfThis = Orientation.chainOfOrientation(previousType).size();
	int sizeOfOrientationOfC = Orientation.chainOfOrientation(c.previousType).size();

	if (sizeOfOrientationOfThis != sizeOfOrientationOfC) {
	    return (sizeOfOrientationOfThis < sizeOfOrientationOfC) ? -1 : 1;
	}

	if (!Type.isAncillary(type)) {
	    return 1;
	}

	if (!Type.isAncillary(c.type)) {
	    return -1;
	}

	return 0;
    }

    private int compareType(OrganisationUnit c) {
	if (type < c.type) {
	    return -1;
	}
	if (type > c.type) {
	    return 1;
	}
	return 0;
    }

    private int compareContent(OrganisationUnit c) {
	if (Type.isKnown(type)) {
	    return ComparatorCorrelations.valueOf(Type.stringValue(type)).getComparator().compare(this, c);
	} else {
	    return SameTypeComparator.ALPHABETICAL_ORDERING.compare(this, c);
	}
    }

    @Override
    public int compareTo(OrganisationUnit c) {
	int res = compareOrientation(c);
	if (res != 0) {
	    return res;
	}

	res = compareType(c);
	if (res != 0) {
	    return res;
	}

	return compareContent(c);
    }
}
