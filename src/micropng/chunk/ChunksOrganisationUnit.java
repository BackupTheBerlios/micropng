package micropng.chunk;

import java.util.Iterator;

import micropng.ChunkSequence;

public class ChunksOrganisationUnit {

    private ChunkSequence chunks;
    private ChunkBehaviour behaviour;

    public ChunksOrganisationUnit(ChunkSequence chunks, ChunkBehaviour behaviour) {
	this.chunks = chunks;
	this.behaviour = behaviour;
    }

    public ChunkSequence getChunks() {
        return chunks;
    }

    public ChunkBehaviour getBehaviour() {
        return behaviour;
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
}
