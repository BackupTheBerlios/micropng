package micropng.micropng;


import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.Type;
import micropng.commonlib.DataDumper;
import micropng.commonlib.StreamFilter;
import micropng.contentview.IDATContent;
import micropng.encodingview.EncodingLayerDecoder;
import micropng.zlib.ZlibDecoder;

public class FullIDATDecoder extends StreamFilter {

    private class DecoderThread implements Runnable {

	@Override
	public void run() {
	    ChunkSequence idatSequence = new ChunkSequence();
	    IDATContent idatContent;
	    ZlibDecoder zlibDecoder = new ZlibDecoder();
	    DataDumper dataDumper = new DataDumper();
	    EncodingLayerDecoder encodingLayerDecoder = new EncodingLayerDecoder(chunkSequence);

	    for (Chunk c : chunkSequence) {
		if (c.getType() == Type.IDAT.toInt()) {
		    idatSequence.add(c);
		}
	    }

	    idatContent = new IDATContent(idatSequence);
	    idatContent.connect(zlibDecoder);
	    zlibDecoder.connect(encodingLayerDecoder);
	    encodingLayerDecoder.connect(dataDumper);

	    idatContent.start();
	    zlibDecoder.start();
	    encodingLayerDecoder.start();
	    dataDumper.start();
	}
    }

    private ChunkSequence chunkSequence;

    public FullIDATDecoder(ChunkSequence chunkSequence) {
	this.chunkSequence = chunkSequence;
    }

    public void decode() {
	new Thread(new DecoderThread()).start();
    }
}
