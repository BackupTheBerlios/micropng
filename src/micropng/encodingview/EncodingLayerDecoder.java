package micropng.encodingview;

import java.util.ArrayList;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.Type;
import micropng.commonlib.StreamFilter;
import micropng.micropng.CodecInfo;

public class EncodingLayerDecoder extends StreamFilter {
    private class EncodingLayerDecoderThread implements Runnable {

	@Override
	public void run() {
	    Interlace deInterlacer = null;
	    ArrayList<StreamFilter> streamFilters = new ArrayList<StreamFilter>(4);
	    StreamFilter end = null;

	    switch (codecInfo.getInterlaceMethod()) {
	    case NONE:
		deInterlacer = new NoneInterlace(codecInfo.getSize());
		break;
	    case ADAM7:
		deInterlacer = new Adam7Interlace(true, codecInfo);
		break;
	    }

	    switch (codecInfo.getFilterMethod()) {
	    case METHOD_0:
		Filter filter = new Filter(codecInfo, deInterlacer);
		shareCurrentInputChannel(filter);
		end = filter;
		streamFilters.add(filter);
		break;
	    }

	    if (codecInfo.getSampleDepth() != 8) {
		SampleSplitter splitter = new SampleSplitter(codecInfo, deInterlacer);
		end.connect(splitter);
		streamFilters.add(splitter);
		end = splitter;
	    }

	    if (codecInfo.getInterlaceMethod() == InterlaceMethod.ADAM7) {
		end.connect(deInterlacer);
		streamFilters.add(deInterlacer);
		end = deInterlacer;
	    }

	    if (codecInfo.isPalette()) {
		PaletteLookUp paletteLookUp = new PaletteLookUp(chunkSequence);
		end.connect(paletteLookUp);
		streamFilters.add(paletteLookUp);
		end = paletteLookUp;
	    }

	    shareCurrentOutputChannel(end);

	    for (StreamFilter streamFilter : streamFilters) {
		streamFilter.start();
	    }
	}
    }

    private ChunkSequence chunkSequence;
    private CodecInfo codecInfo;

    public EncodingLayerDecoder(ChunkSequence chunkSequence) {
	this.chunkSequence = chunkSequence;
	Chunk header = chunkSequence.getChunk(Type.IHDR);
	codecInfo = new CodecInfo(header);
    }

    @Override
    public void start() {
	new Thread(new EncodingLayerDecoderThread()).start();
    }
}
