package micropng.chunk;

import java.util.HashMap;

public enum ChunkBehaviourFactory {
    MAPS;

    private static final int defaultMapSize = 127;

    private HashMap<Integer, Integer> orientationsMap = new HashMap<Integer, Integer>(defaultMapSize);
    private HashMap<Integer, SameTypeComparator> comparatorsMap = new HashMap<Integer, SameTypeComparator>(defaultMapSize);

    ChunkBehaviourFactory() {
	for (KnownChunkType type : KnownChunkType.values()) {
	    int typeInt = Type.intValue(type.toString());
	    String typeName = type.name();

	    orientationsMap.put(typeInt, Integer.parseInt(MandatoryChunkOrientation.valueOf(typeName).getOrientation()));
	    comparatorsMap.put(typeInt, KnownSameTypeComparatorCorrelations.valueOf(typeName).getComparator());
	}
    }

    public ChunkBehaviour getChunkBehaviour(int type, int lastMandatory) {
	return new ChunkBehaviour(getOrientation(type, lastMandatory), getComparator(type));
    }

    //TODO: clean up this mess
    private int getOrientation(int type, int lastMandatory) {
	int res;
	if (orientationsMap.containsKey(type)) {
	    Integer value = orientationsMap.get(type);
	    if (value == null) {
		res = 0;
	    } else {
		res = value;
	    }
	} else {
	    res = lastMandatory;
	}
	return res;
    }

    private SameTypeComparator getComparator(int type) {
	SameTypeComparator res;
	if (comparatorsMap.containsKey(type)) {
	    res = comparatorsMap.get(type);
	} else {
	    res = SameTypeComparator.ALPHABETICAL_ORDERING;
	}
	return res;
    }
}
