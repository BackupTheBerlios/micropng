package micropng.chunkview.chunk;

import micropng.commonlib.FourByteConverter;
import micropng.commonlib.Queue;

public enum CRCCalculator {
    ;

    private final static int[] crcTable = new int[256];

    static {
	makeCrcTable();
    }

    private static void makeCrcTable() {
	for (int i = 0; i < 256; i++) {
	    int c = i;
	    for (int j = 0; j < 8; j++) {
		if ((c & 0x00000001) != 0) {
		    c = 0xedb88320 ^ (c >>> 1);
		} else {
		    c >>>= 1;
		}
	    }
	    crcTable[i] = c;
	}
    }

    public static int calculate(int type, DataField data) {
	int crc = 0xffffffff;
	int[] typeArray = FourByteConverter.intArrayValue(type);
	Queue input = data.getStream();

	for (int i = 0; i < 4; i++) {
	    crc = crcTable[(crc ^ typeArray[i]) & 0x000000ff] ^ (crc >>> 8);
	}

	int currentValue = input.take();
	while (currentValue != -1) {
	    crc = crcTable[(crc ^ currentValue) & 0x000000ff] ^ (crc >>> 8);
	    currentValue = input.take();
	}

	return crc ^ 0xffffffff;
    }
}
