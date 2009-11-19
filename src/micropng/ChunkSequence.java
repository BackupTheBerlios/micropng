package micropng;

import java.util.ArrayList;
import java.util.Iterator;

import micropng.chunk.Chunk;

public class ChunkSequence implements Iterable<Chunk> {

    private ArrayList<Chunk> chunkList;

    public ChunkSequence() {
	this.chunkList = new ArrayList<Chunk>();
    }

    @Override
    public Iterator<Chunk> iterator() {
	return chunkList.iterator();
    }

    public void append(Chunk c) {
	chunkList.add(c);
    }
}
