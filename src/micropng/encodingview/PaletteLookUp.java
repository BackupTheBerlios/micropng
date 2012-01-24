package micropng.encodingview;

import micropng.chunkview.ChunkSequence;
import micropng.commonlib.StreamFilter;
import micropng.contentview.Palette;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.Type;

public class PaletteLookUp extends StreamFilter {

    private class PaletteLookUpThread implements Runnable {

	@Override
	public void run() {
	    int next = in();
	    while (next != -1) {
		int[] nextEntry = palette.lookUp(next);
		out(nextEntry[0]);
		out(nextEntry[1]);
		out(nextEntry[2]);
		next = in();
	    }
	    done();
	}
    }

    private Palette palette;

    public PaletteLookUp(ChunkSequence chunkSequence) {
	Chunk paletteChunk = chunkSequence.getChunk(Type.PLTE);
	palette = new Palette(paletteChunk);
    }

    public void start() {
	new Thread(new PaletteLookUpThread()).start();
    }
}
