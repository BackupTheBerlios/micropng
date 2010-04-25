package micropng.chunk;

import micropng.Queue;

public class CRCCalculator {

    private Queue input;
    private int[] crcTable;
    private int crc;

    public CRCCalculator(Queue input, int[] type) {
	this.input = input;
	crcTable = new int[256];
	crc = 0xffffffff;

	makeCrcTable();

	for (int i = 0; i < 4; i++) {
	    crc = crcTable[(crc ^ type[i]) & 0x000000ff] ^ (crc >>> 8);
	}
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

    public void run() {
	try {
	    int currentValue = input.take();
	    while (currentValue != -1) {
		crc = crcTable[(crc ^ currentValue) & 0x000000ff] ^ (crc >>> 8);
		currentValue = input.take();
	    }
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

    public int getResult() {
	return crc ^ 0xffffffff;
    }

    public boolean compareTo(int referenceValue) {
	return getResult() == referenceValue;
    }
}
