package micropng.pngio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.commonlib.FourByteConverter;
import micropng.fileview.PNGProperties;

public class FileWriter {

    @SuppressWarnings({ "serial" })
    private class CanNotCreateFileException extends IOException {

    }

    public void writeSequence(File outputFileObject, ChunkSequence chunkSequence)
	    throws IOException {
	final RandomAccessFile outputFile = new RandomAccessFile(outputFileObject, "rw");

	outputFile.write(PNGProperties.getSignature());

	for (final Chunk c : chunkSequence) {
	    outputFile.write(FourByteConverter.byteArrayValue(c.getDataSize()));
	    outputFile.write(FourByteConverter.byteArrayValue(c.getType()));
	    outputFile.write(c.getData().getArray());
	    outputFile.write(FourByteConverter.byteArrayValue(c.getCrc()));
	}
    }
}
