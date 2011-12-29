package micropng.micropng;

import java.nio.ByteBuffer;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.Type;
import micropng.encodingview.FilterMethod;
import micropng.encodingview.InterlaceMethod;

public class EncodingAnalyzer {

    public CodecInfo analyze(ChunkSequence chunkSequence) {
	CodecInfo res = new CodecInfo();
	Chunk header = chunkSequence.getChunk(Type.IHDR.toInt());
	ByteBuffer byteBuffer = ByteBuffer.wrap(header.getData().getArray());
	int width = byteBuffer.getInt();
	int height = byteBuffer.getInt();
	Dimensions size = new Dimensions(width, height);
	int bitDepth = byteBuffer.get();
	int colorType = byteBuffer.get();
	int compressionMethod = byteBuffer.get();
	FilterMethod filterMethod = FilterMethod.getMethod(byteBuffer.get());
	InterlaceMethod interlaceMethod = InterlaceMethod.getMethod(byteBuffer.get());

	res.setSize(size);
	res.setBitDepth(bitDepth);
	res.setPalette((colorType & 0x0001) != 0);
	res.setColour((colorType & 0x0010) != 0);
	res.setAlphaChannel((colorType & 0x0100) != 0);
	res.setFilterMethod(filterMethod);
	res.setInterlaceMethod(interlaceMethod);

	return res;
    }
}
