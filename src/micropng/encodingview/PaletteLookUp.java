package micropng.encodingview;

import micropng.chunkview.ChunkSequence;
import micropng.commonlib.StreamFilter;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.Type;

public class PaletteLookUp extends StreamFilter {

    private class PaletteLookUpThread implements Runnable {

	@Override
	public void run() {
	    int next = in();
	    while (next != -1) {
		out(lookUpTable[next][0]);
		out(lookUpTable[next][1]);
		out(lookUpTable[next][2]);
		next = in();
	    }
	    done();
	}
    }

    private int[][] lookUpTable;

    public PaletteLookUp(ChunkSequence chunkSequence) {
	Chunk paletteChunk = chunkSequence.getChunk(Type.PLTE.toInt());
	byte[] chunkData = paletteChunk.getData().getArray();
	int numberOfPaletteEntries = chunkData.length / 3;
	int pos = 0;
	lookUpTable = new int[numberOfPaletteEntries][3];
	for (int i = 0; i < numberOfPaletteEntries; i++) {
	    lookUpTable[i][0] = 0xff & chunkData[pos++];
	    lookUpTable[i][1] = 0xff & chunkData[pos++];
	    lookUpTable[i][2] = 0xff & chunkData[pos++];
	}
    }

    public void start() {
	new Thread(new PaletteLookUpThread()).start();
    }
}
