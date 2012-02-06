package micropng.encodingview;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.Type;
import micropng.commonlib.StreamFilter;
import micropng.fileview.ColourType;
import micropng.micropng.CodecInfo;

public class TransparencyLookUp extends StreamFilter {

    private class TransparencyLookUpThread implements Runnable {

	@Override
	public void run() {
	    int next = in();
	    while (next != -1) {

		next = in();
	    }
	    done();
	}
    }

    private static final int MASK = 0xff;

    private void greyscaleLookUp(Chunk transparencyChunk) {
	// byte[] chunkData = transparencyChunk.getData().getArray();
	// int transparentValue = ((MASK & chunkData[0]) << 8) | chunkData[1];
    }

    private void truecolorLookUp(Chunk transparencyChunk) {
	byte[] chunkData = transparencyChunk.getData().getArray();
	int[] transparentValue = new int[3];
	for (int i = 0; i < transparentValue.length; i++) {
	    transparentValue[i] = ((MASK & chunkData[2 * i]) << 8) | chunkData[2 * i + 1];
	}
    }

    private void indexedColourLookUp(Chunk transparencyChunk) {
	byte[] chunkData = transparencyChunk.getData().getArray();
	int[] alphaValues = new int[chunkData.length];
	for (int i = 0; i < chunkData.length; i++) {
	    alphaValues[i] = MASK & chunkData[i];
	}
    }

    public void transparencyLookUp(CodecInfo codecInfo, ChunkSequence chunkSequence) {
	Chunk transparencyChunk = chunkSequence.getChunk(Type.tRNS);
	if (transparencyChunk != null) {
	    ColourType colourType = codecInfo.getColourType();
	    switch (colourType) {
	    case GREYSCALE:
		greyscaleLookUp(transparencyChunk);
		break;
	    case TRUECOLOR:
		truecolorLookUp(transparencyChunk);
		break;
	    case INDEXED_COLOUR:
		indexedColourLookUp(transparencyChunk);
		break;
	    }
	}
    }

    public void start() {
	new Thread(new TransparencyLookUpThread()).start();
    }
}
