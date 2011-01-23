package micropng.pngio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.ChunkReader;
import micropng.fileview.PNGProperties;

public class FileReader {

    public FileReader() {
    }

    public ChunkSequence readSequence(File inputFileObject) throws IOException {
	ChunkSequence res = new ChunkSequence();
	RandomAccessFile inputFile = new RandomAccessFile(inputFileObject, "r");
	long filePointerPosition = PNGProperties.getSignature().length;
	ChunkReader factory = new ChunkReader();

	inputFile.seek(filePointerPosition);

	do {
	    Chunk nextChunk = factory.readChunk(inputFile);

	    if (inputFile.getChannel().lock(filePointerPosition, nextChunk.getDataSize() + 12, true) == null) {
		throw new ConcurrentLockException();
	    }

	    res.add(nextChunk);
	    filePointerPosition = inputFile.getFilePointer();
	} while (inputFile.getFilePointer() < inputFileObject.length());

	return res;
    }
}
