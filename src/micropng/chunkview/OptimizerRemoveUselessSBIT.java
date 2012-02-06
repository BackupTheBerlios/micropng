package micropng.chunkview;

import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.Type;
import micropng.contentview.SignificantBits;
import micropng.micropng.CodecInfo;

public class OptimizerRemoveUselessSBIT {
    public void optimize(ChunkSequence chunkSequence) {
	final Chunk sBITChunk = chunkSequence.getChunk(Type.sBIT);
	if (sBITChunk != null) {
	    SignificantBits significantBits = new SignificantBits(sBITChunk);
	    CodecInfo codecInfo = new CodecInfo(chunkSequence.getChunk(Type.IHDR));
	    int numberOfChannels = codecInfo.numberOfChannels();
	    int sampleDepth = codecInfo.getSampleDepth();
	    boolean useful = false;
	    int i = 0;

	    while ((!useful) && (i < numberOfChannels)) {
		useful = (significantBits.significantBitsInChannel(i) < sampleDepth);
		i++;
	    }

	    if (!useful) {
		chunkSequence.remove(sBITChunk);
	    }
	}
    }
}
