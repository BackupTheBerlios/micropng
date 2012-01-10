package micropng.encodingview;

import java.nio.ByteBuffer;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.Type;
import micropng.commonlib.StreamFilter;
import micropng.micropng.CodecInfo;
import micropng.micropng.Dimensions;

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
	codecInfo = new CodecInfo();
	generateCodecInfo();
    }

    private void generateCodecInfo() {
	Chunk header = chunkSequence.getChunk(Type.IHDR.toInt());
	ByteBuffer byteBuffer = ByteBuffer.wrap(header.getData().getArray());
	int width = byteBuffer.getInt();
	int height = byteBuffer.getInt();
	Dimensions size = new Dimensions(width, height);
	int bitDepth = byteBuffer.get();
	int colorType = byteBuffer.get();
	CompressionMethod compressionMethod = CompressionMethod.getMethod(byteBuffer.get());
	FilterMethod filterMethod = FilterMethod.getMethod(byteBuffer.get());
	InterlaceMethod interlaceMethod = InterlaceMethod.getMethod(byteBuffer.get());

	codecInfo.setSize(size);
	codecInfo.setBitDepth(bitDepth);
	codecInfo.setColourType(colorType);
	codecInfo.setCompressionMethod(compressionMethod);
	codecInfo.setFilterMethod(filterMethod);
	codecInfo.setInterlaceMethod(interlaceMethod);
    }

    public void start() {
	new Thread(new EncodingLayerDecoderThread()).start();
    }
}
