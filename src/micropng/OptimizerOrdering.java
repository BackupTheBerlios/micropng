package micropng;

import java.util.Hashtable;
import java.util.TreeMap;

import micropng.chunk.Chunk;

public class OptimizerOrdering {
    private Hashtable<Integer, PartialOrderRelationPost> handlerLookUpTable;
    private TreeMap<PartialOrderRelationPost, Chunk> map;

    public OptimizerOrdering() {
	handlerLookUpTable = new Hashtable<Integer, PartialOrderRelationPost>();
	map = new TreeMap<PartialOrderRelationPost, Chunk>();
    }

    public void addTypeHandler(int type, PartialOrderRelationPost typeHandler) {
	handlerLookUpTable.put(type, typeHandler);
    }

    public PartialOrderRelationPost getTypeHandler(int type) {
	return handlerLookUpTable.get(type);
    }

    private PartialOrderRelationPost lookUpHandler(int type) {
	return handlerLookUpTable.get(type);
    }

    public ChunkSequence optimize(ChunkSequence seq) {
	ChunkSequence res = new ChunkSequence();
	for (Chunk c : seq) {
	    map.put(lookUpHandler(c.getTypeInt()), c);
	}
	for (Chunk c : map.values()) {
	    res.append(c);
	}
	return res;
    }
}
