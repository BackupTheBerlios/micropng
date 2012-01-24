package micropng.micropng;

import micropng.chunkscontentview.IDATContent;
import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.Type;
import micropng.commonlib.DataDumper;
import micropng.commonlib.StreamFilter;
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

    private ChunkSequence chunkSequence;

    public FullIDATDecoder(ChunkSequence chunkSequence) {
	this.chunkSequence = chunkSequence;
    }

    public void decode() {
	new Thread(new DecoderThread()).start();
    }
}
