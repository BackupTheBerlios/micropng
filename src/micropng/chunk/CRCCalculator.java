package micropng.chunk;

import micropng.Queue;

public class CRCCalculator {

    private int[] crcTable;

    public CRCCalculator() {
	crcTable = new int[256];
	makeCrcTable();
    }

    private void makeCrcTable() {
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

    public int calculate(int[] type, Queue input) {
	int crc = 0xffffffff;

	for (int i = 0; i < 4; i++) {
	    crc = crcTable[(crc ^ type[i]) & 0x000000ff] ^ (crc >>> 8);
	}

	try {
	    int currentValue = input.take();
	    while (currentValue != -1) {
		crc = crcTable[(crc ^ currentValue) & 0x000000ff] ^ (crc >>> 8);
		currentValue = input.take();
	    }
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

	return crc ^ 0xffffffff;
    }
}
