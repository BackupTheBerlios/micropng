package micropng.micropng;

import micropng.chunkscontentview.IDATContent;
import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.Type;
import micropng.commonlib.Sink;
import micropng.commonlib.StreamFilter;
import micropng.encodingview.EncodingLayerDecoder;
import micropng.zlib.ZlibDecoder;

public class FullIDATDecoder extends StreamFilter {
    private final ChunkSequence chunkSequence;

    public FullIDATDecoder(ChunkSequence chunkSequence) {
	this.chunkSequence = chunkSequence;
    }

    public void decode() {
	final ChunkSequence idatSequence = new ChunkSequence();
	final IDATContent idatContent;
	final ZlibDecoder zlibDecoder = new ZlibDecoder();
	final Sink dataDumper = new Sink();
	final EncodingLayerDecoder encodingLayerDecoder = new EncodingLayerDecoder(chunkSequence);

	int IDATType = Type.IDAT.toInt();

	for (Chunk c : chunkSequence) {
	    if (c.getType() == IDATType) {
		idatSequence.add(c);
	    }
	}

	idatContent = new IDATContent(idatSequence);
	idatContent.connect(zlibDecoder);
	idatContent.start();
	zlibDecoder.connect(encodingLayerDecoder);
	zlibDecoder.start();
	encodingLayerDecoder.connect(dataDumper);
	encodingLayerDecoder.start();
	dataDumper.start();
    }
}
