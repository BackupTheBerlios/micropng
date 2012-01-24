package micropng.encodingview;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.Type;
import micropng.commonlib.StreamFilter;
import micropng.micropng.CodecInfo;

public class EncodingLayerDecoder extends StreamFilter {
    private class EncodingLayerDecoderThread implements Runnable {

	@Override
	public void run() {
	    Filter filter = null;
	    Interlace deInterlacer = null;
	    PaletteLookUp paletteLookUp = null;
	    SampleSplitter splitter = new SampleSplitter(codecInfo);

	    switch (codecInfo.getFilterMethod()) {
	    case METHOD_0:
		filter = new Filter(codecInfo);
		shareCurrentInputChannel(filter);
		break;
	    }

	    switch (codecInfo.getInterlaceMethod()) {
	    case NONE:
		deInterlacer = new NoneInterlace(codecInfo.getSize());
		break;
	    case ADAM7:
		deInterlacer = new Adam7Interlace(true, codecInfo);
		break;
	    }

	    if (codecInfo.isPalette()) {
		paletteLookUp = new PaletteLookUp(chunkSequence);
		deInterlacer.connect(paletteLookUp);
		shareCurrentOutputChannel(paletteLookUp);
		paletteLookUp.start();
	    } else {
		shareCurrentOutputChannel(deInterlacer);
	    }

	    filter.connect(splitter);
	    splitter.connect(deInterlacer);

	    filter.start(deInterlacer.getGraphicsSizes());
	    splitter.start(deInterlacer.getGraphicsSizes());
	    deInterlacer.start();
	}
    }

    private ChunkSequence chunkSequence;
    private CodecInfo codecInfo;

    public EncodingLayerDecoder(ChunkSequence chunkSequence) {
	this.chunkSequence = chunkSequence;
	Chunk header = chunkSequence.getChunk(Type.IHDR);
	codecInfo = new CodecInfo(header);
    }

    public void start() {
	new Thread(new EncodingLayerDecoderThread()).start();
    }
}
