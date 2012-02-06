package micropng.chunkview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.Type;

public class ChunkSequence implements Collection<Chunk> {

    private final ArrayList<Chunk> chunkList = new ArrayList<Chunk>();

    public Chunk elementAt(int pos) {
	return chunkList.get(pos);
    }

    @Override
    public Iterator<Chunk> iterator() {
	return chunkList.iterator();
    }

    @Override
    public boolean add(Chunk c) {
	return chunkList.add(c);
    }

    @Override
    public boolean addAll(Collection<? extends Chunk> c) {
	return chunkList.addAll(c);
    }

    @Override
    public void clear() {
	chunkList.clear();
    }

    @Override
    public boolean contains(Object o) {
	return chunkList.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
	return chunkList.containsAll(c);
    }

    @Override
    public boolean isEmpty() {
	return chunkList.isEmpty();
    }

    @Override
    public boolean remove(Object o) {
	return chunkList.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
	return chunkList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
	return chunkList.retainAll(c);
    }

    @Override
    public int size() {
	return chunkList.size();
    }

    @Override
    public Object[] toArray() {
	return chunkList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
	return chunkList.toArray(a);
    }

    /**
     * Return the first Chunk of type {@code type}.
     * 
     * Returns the first occurring Chunk of the specified type in this
     * ChunkSequence regardless of any other Chunks of the same type. If there
     * is no Chunk of the specified type, null is returned.
     * 
     * @param type the type of Chunk encoded as int 
     * @return the first Chunk of type {@code type} or null if there is none
     */
    public Chunk getChunk(int type) {
	for (Chunk c : chunkList) {
	    if (c.getType() == type) {
		return c;
	    }
	}
	return null;
    }

    /**
     * Return the first Chunk of the known type {@code type}.
     * 
     * Returns the first occurring Chunk of the specified known type in this
     * ChunkSequence regardless of any other Chunks of the same type. If there
     * is no Chunk of the specified type, null is returned.
     * 
     * @param type
     *            the known type of the Chunk
     * @return the first Chunk of type {@code type} or null if there is none
     */

    public Chunk getChunk(Type type) {
	return getChunk(type.toInt());
    }
}
