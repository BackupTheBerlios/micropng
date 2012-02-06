package micropng.contentview;

import micropng.chunkview.chunk.Chunk;

public class Palette {

    private final int[][] lookUpTable;

    public Palette(Chunk paletteChunk) {
	final byte[] chunkData = paletteChunk.getData().getArray();
	final int numberOfPaletteEntries = chunkData.length / 3;
	int pos = 0;
	lookUpTable = new int[numberOfPaletteEntries][3];

	for (int[] lookUpTableElement : lookUpTable) {
	    lookUpTableElement[0] = 0xff & chunkData[pos++];
	    lookUpTableElement[1] = 0xff & chunkData[pos++];
	    lookUpTableElement[2] = 0xff & chunkData[pos++];
	}
    }

    public int[] lookUp(int position) {
	return lookUpTable[position];
    }
}
