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
	    int bitsPerSample = codecInfo.getBitDepth();
	    Filter filter = null;
	    DeInterlacerFilterController deInterlacerFilterController = null;
	    Interlace deInterlacer = null;
	    PaletteLookUp paletteLookUp = null;

	    switch (codecInfo.getFilterMethod()) {
	    case METHOD_0:
		filter = new Filter(codecInfo.numberOfChannels(), codecInfo.getBitDepth());
		shareCurrentInputChannel(filter);
		break;
	    }

	    switch (codecInfo.getInterlaceMethod()) {
	    case NONE:
		deInterlacerFilterController = new NoneInterlacerDecoderFilterController(codecInfo
			.getSize(), filter);
		deInterlacer = new NoneInterlace();
		break;
	    case ADAM7:
		deInterlacerFilterController = new Adam7DecoderFilterController(
			codecInfo.getSize(), filter);
		deInterlacer = new Adam7Interlace(true, codecInfo);
		break;
	    }

	    if (bitsPerSample != 8) {
		SampleSplitter splitter = new SampleSplitter(bitsPerSample);
		filter.connect(splitter);
		splitter.connect(deInterlacer);
	    } else {
		filter.connect(deInterlacer);
	    }

	    if (codecInfo.isPalette()) {
		paletteLookUp = new PaletteLookUp(chunkSequence);
		deInterlacer.connect(paletteLookUp);
		shareCurrentInputChannel(paletteLookUp);
	    } else {
		shareCurrentOutputChannel(deInterlacer);
	    }

	    deInterlacerFilterController.start();
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
