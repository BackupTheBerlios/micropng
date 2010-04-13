/*package micropng.chunk;

import java.util.ArrayList;
import java.util.Iterator;

import micropng.chunk.Chunk;
import micropng.pngoptimization.OptimizerOrdering;

public class OrderingKey implements Comparable<OrderingKey> {

    private Chunk chunk;
    private int position;
    private DataComparator comparator;
    private OptimizerOrdering optimizer;

    public OrderingKey(Chunk chunk, int position, DataComparator comparator, OptimizerOrdering optimizer) {
	this.chunk = chunk;
	this.position = position;
	this.comparator = comparator;
	this.optimizer = optimizer;
    }

    public Chunk getChunk() {
	return chunk;
    }

    public int getPosition() {
	return position;
    }

    private int compareTypeAlphabetically(OrderingKey otherKey) {
	int myType = chunk.getTypeInt();
	int otherType = otherKey.chunk.getTypeInt();
	if (myType < otherType) {
	    return -1;
	}
	if (myType > otherType) {
	    return 1;
	}
	return comparator.compare(this, otherKey);
    }

    private int compareWithChunkType(OrderingKey otherKey) {
	if (!chunk.isAncillary()) {
	    if (otherKey.chunk.isAncillary()) {
		return 1;
	    } else {
		return compareTypeAlphabetically(otherKey);
	    }
	} else {
	    if (otherKey.chunk.isAncillary()) {
		return compareTypeAlphabetically(otherKey);
	    } else {
		return -1;
	    }
	}
    }

    private ArrayList<Integer> getRelationList() {
	int nextPost = optimizer.getPartialRelation(chunk.getTypeInt());
	ArrayList<Integer> res = new ArrayList<Integer>();
	res.add(0, nextPost);
	while (nextPost != 0) {
	    nextPost = optimizer.getPartialRelation(nextPost);
	    res.add(0, nextPost);
	}
	return res;
    }

    @Override
    public int compareTo(OrderingKey otherKey) {
	int res = 0;
	ArrayList<Integer> myRelationList = getRelationList();
	ArrayList<Integer> otherRelationList = otherKey.getRelationList();
	Iterator<Integer> myIterator = myRelationList.iterator();
	Iterator<Integer> otherIterator = otherRelationList.iterator();

	while (myIterator.hasNext() && otherIterator.hasNext()) {
	    Integer my = myIterator.next();
	    Integer other = otherIterator.next();
	    if (my < other) {
		res = -1;
	    }
	    if (my > other) {
		res = 1;
	    }
	}
	if (otherIterator.hasNext()) {
	    res = -1;
	}
	if (myIterator.hasNext()) {
	    res = 1;
	}
	if (res == 0) {
	    res = compareWithChunkType(otherKey);
	}

	return res;
    }
}
*/