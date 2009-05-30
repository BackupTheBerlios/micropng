package micropng;

import java.util.AbstractSequentialList;
import java.util.ListIterator;

import micropng.chunk.Chunk;


public class ChunkList extends AbstractSequentialList<Chunk> {

    class ChunkListIterator implements ListIterator<Chunk> {

	@Override
	public void add(Chunk e) {
	    // TODO Auto-generated method stub

	}

	@Override
	public boolean hasNext() {
	    // TODO Auto-generated method stub
	    return false;
	}

	@Override
	public boolean hasPrevious() {
	    // TODO Auto-generated method stub
	    return false;
	}

	@Override
	public Chunk next() {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public int nextIndex() {
	    // TODO Auto-generated method stub
	    return 0;
	}

	@Override
	public Chunk previous() {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public int previousIndex() {
	    // TODO Auto-generated method stub
	    return 0;
	}

	@Override
	public void remove() {
	    // TODO Auto-generated method stub

	}

	@Override
	public void set(Chunk e) {
	    // TODO Auto-generated method stub

	}
    }

    @Override
    public ListIterator<Chunk> listIterator(int index) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public int size() {
	// TODO Auto-generated method stub
	return 0;
    }
}
