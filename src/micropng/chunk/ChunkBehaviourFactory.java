package micropng.chunk;

import java.util.HashMap;

public enum ChunkBehaviourFactory {
    ;

    private static final int mapSize = 127;

    static private HashMap<Integer, ChunkType> orientationsMap = new HashMap<Integer, ChunkType>(mapSize);
    static private HashMap<Integer, SameTypeComparator> comparatorsMap = new HashMap<Integer, SameTypeComparator>(mapSize);

    static {
	for (ChunkType type : ChunkType.values()) {
	    int typeInt = type.toInt();
	    String typeName = type.name();

	    orientationsMap.put(typeInt, MandatoryChunkOrientation.valueOf(typeName).getOrientation());
	    comparatorsMap.put(typeInt, KnownSameTypeComparatorCorrelations.valueOf(typeName).getComparator());
	}
    }

    public static ChunkBehaviour getChunkBehaviour(int type, ChunkType lastMandatory) {
	return new ChunkBehaviour(getOrientation(type, lastMandatory), getComparator(type));
    }

    // TODO: clean up this mess
    private static ChunkType getOrientation(int type, ChunkType lastMandatory) {
	ChunkType res;
	if (orientationsMap.containsKey(type)) {
	    ChunkType value = orientationsMap.get(type);
	    if (value == null) {
		res = null;
	    } else {
		res = value;
	    }
	} else {
	    res = lastMandatory;
	}
	return res;
    }

    private static SameTypeComparator getComparator(int type) {
	SameTypeComparator res;
	if (comparatorsMap.containsKey(type)) {
	    res = comparatorsMap.get(type);
	} else {
	    res = SameTypeComparator.ALPHABETICAL_ORDERING;
	}
	return res;
    }
}
