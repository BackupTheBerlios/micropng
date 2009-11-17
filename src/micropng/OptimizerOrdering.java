package micropng;

import java.util.Hashtable;
import java.util.SortedMap;

import micropng.chunk.Chunk;

public class OptimizerOrdering {
    private Hashtable<Integer, OrderingHandler> handlerLookUpTable;
    private SortedMap<OrderingHandler, Chunk> res;

    public OptimizerOrdering() {
	handlerLookUpTable = new Hashtable<Integer, OrderingHandler> ();
    }

    public void addTypeHandler(int type, OrderingHandler typeHandler) {
	handlerLookUpTable.put(type, typeHandler);
    }

    private OrderingHandler lookUpHandler(int type) {
	OrderingHandler res = handlerLookUpTable.get(type);
	if (res == null) {
	    
	}
	return res;
    }

}
