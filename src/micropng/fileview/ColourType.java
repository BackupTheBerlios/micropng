package micropng.fileview;

import java.util.HashMap;

public enum ColourType {
    GREYSCALE(0, 1),
    TRUECOLOR(2, 3),
    INDEXED_COLOUR(3, 1),
    GREYSCALE_WITH_ALPHA(4, 2),
    TRUECOLOR_WITH_ALPHA(6, 4);

    private int intType;
    private int numberOfChannels;
    private static final HashMap<Integer, ColourType> lookUpTable;

    static {
	ColourType[] values = ColourType.values();
	lookUpTable = new HashMap<Integer, ColourType>(values.length * 2);

	for (ColourType type : values) {
	    lookUpTable.put(type.intType, type);
	}
    }

    private ColourType(int intType, int numberOfChannels) {
	this.intType = intType;
	this.numberOfChannels = numberOfChannels;
    }

    public static ColourType byInt(int value) {
	return lookUpTable.get(value);
    }

    public boolean isPalette() {
	return (intType & 0x01) != 0;
    }

    public boolean isColour() {
	return (intType & 0x02) != 0;
    }

    public boolean hasAlphaChannel() {
	return (intType & 0x04) != 0;
    }

    public int numberOfChannels() {
	return numberOfChannels;
    }
}
