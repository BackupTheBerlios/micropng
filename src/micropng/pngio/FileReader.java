package micropng.pngio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import micropng.ChunkSequence;
import micropng.chunk.Chunk;
import micropng.chunk.ChunkFactory;

public class FileReader {

    public FileReader() {
    }

    public ChunkSequence readSequence(File inputFileObject) throws IOException {
	ChunkSequence res = new ChunkSequence();
	RandomAccessFile inputFile = new RandomAccessFile(inputFileObject, "r");
	long filePointerPosition = PNGProperties.getSignature().length;
	ChunkFactory factory = new ChunkFactory();

	inputFile.seek(filePointerPosition);

	do {
	    Chunk nextChunk = factory.readChunk(inputFile);

	    if (inputFile.getChannel().lock(filePointerPosition, nextChunk.getSize(), true) == null) {
		throw new ConcurrentLockException();
	    }

	    res.append(nextChunk);
	    filePointerPosition = inputFile.getFilePointer();
	} while (inputFile.getFilePointer() < inputFileObject.length());

	return res;
    }
}
