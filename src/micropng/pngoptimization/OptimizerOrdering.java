package micropng.pngoptimization;

import java.util.Hashtable;
import java.util.TreeMap;

import micropng.ChunkSequence;
import micropng.chunk.Chunk;
import micropng.chunk.Type;
import micropng.pngordering.DataComparator;
import micropng.pngordering.DataComparatorPosition;
import micropng.pngordering.OrderingKey;

public class OptimizerOrdering {
    private Hashtable<Integer, Integer> relationLookUpTable;
    private Hashtable<Integer, DataComparator> dataComparatorLookUpTable;
    private TreeMap<OrderingKey, Chunk> map;

    public OptimizerOrdering() {
	relationLookUpTable = new Hashtable<Integer, Integer>();
	dataComparatorLookUpTable = new Hashtable<Integer, DataComparator>();
	map = new TreeMap<OrderingKey, Chunk>();
    }

    public void addPartialRelation(Type type, Type post) {
	addPartialRelation(type.toInt(), post.toInt());
    }

    private void addPartialRelation(int type, int post) {
	relationLookUpTable.put(type, post);
    }

    public void addDataComparator(int type, DataComparator comparator) {
	dataComparatorLookUpTable.put(type, comparator);
    }

    public int getPartialRelation(int type) {
	// System.out.println(type);
	Integer res = relationLookUpTable.get(type);
	if (res == null) {
	    res = 0;
	}
	return res;
    }

    public ChunkSequence optimize(ChunkSequence seq) {
	ChunkSequence res = new ChunkSequence();
	int lastMandatoryType = 0;
	int pos = 0;
	for (Chunk c : seq) {
	    int type = c.getTypeInt();
	    Integer postInteger = relationLookUpTable.get(type);
	    int post = 0;
	    if (postInteger != null) {
		post = postInteger;
	    }
	    DataComparator dataComparator = dataComparatorLookUpTable.get(type);
	    if (post == 0) {
		post = lastMandatoryType;
		addPartialRelation(type, post);
	    }
	    if (dataComparator == null) {
		dataComparator = new DataComparatorPosition();
		addDataComparator(type, dataComparator);
	    }
	    OrderingKey key = new OrderingKey(c, pos, dataComparator, this);
	    if (!c.isAncillary()) {
		lastMandatoryType = c.getTypeInt();
	    }
	    map.put(key, c);
	    pos++;
	}
	for (Chunk c : map.values()) {
	    res.append(c);
	}
	return res;
    }
}
