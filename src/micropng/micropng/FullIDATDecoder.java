package micropng.micropng;

import java.nio.ByteBuffer;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.Type;
import micropng.commonlib.DataDumper;
import micropng.commonlib.StreamFilter;
import micropng.contentview.IDATContent;
import micropng.encodingview.CompressionMethod;
import micropng.encodingview.FilterMethod;
import micropng.encodingview.InterlaceMethod;
import micropng.zlib.ZlibDecoder;

public class FullIDATDecoder extends StreamFilter {

    private class DecoderThread implements Runnable {

	@Override
	public void run() {
	    ChunkSequence idatSequence = new ChunkSequence();
	    IDATContent idatContent;
	    ZlibDecoder zlibDecoder = new ZlibDecoder();
	    DataDumper dataDumper = new DataDumper();

	    for (Chunk c : chunkSequence) {
		if (c.getType() == Type.IDAT.toInt()) {
		    idatSequence.add(c);
		}
	    }

	    idatContent = new IDATContent(idatSequence);
	    idatContent.connect(zlibDecoder);
	    zlibDecoder.connect(dataDumper);

	    idatContent.start();
	    zlibDecoder.start();
	    dataDumper.start();
	}
    }

    ChunkSequence chunkSequence;
    CodecInfo encodingProperties;

    public FullIDATDecoder(ChunkSequence chunkSequence) {
	this.chunkSequence = chunkSequence;
	encodingProperties = new CodecInfo();
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

	encodingProperties.setSize(size);
	encodingProperties.setBitDepth(bitDepth);
	encodingProperties.setPalette((colorType & 0x0001) != 0);
	encodingProperties.setColour((colorType & 0x0010) != 0);
	encodingProperties.setAlphaChannel((colorType & 0x0100) != 0);
	encodingProperties.setCompressionMethod(compressionMethod);
	encodingProperties.setFilterMethod(filterMethod);
	encodingProperties.setInterlaceMethod(interlaceMethod);
    }

    public CodecInfo encodingProperties() {
	return encodingProperties;
    }

    public void decode() {
	new Thread(new DecoderThread()).start();
    }
}
