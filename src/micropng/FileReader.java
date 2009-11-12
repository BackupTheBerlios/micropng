package micropng;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

import micropng.chunk.Chunk;
import micropng.chunk.ChunkFactory;

public class FileReader {

    public FileReader() {
    }

    public LinkedList<Chunk> readSequence(File inputFileObject) throws IOException {
	LinkedList<Chunk> res = new LinkedList<Chunk>();
	RandomAccessFile inputFile = new RandomAccessFile(inputFileObject, "r");
	long filePointerPosition = PNGProperties.getSignature().length;
	inputFile.seek(filePointerPosition);
	ChunkFactory factory = new ChunkFactory();

	do {
	    Chunk nextChunk = factory.readChunk(inputFile);

	    if (inputFile.getChannel().lock(filePointerPosition, nextChunk.getSize(), true) == null) {
		throw new ConcurrentLockException();
	    }

	    res.add(nextChunk);
	    filePointerPosition = inputFile.getFilePointer();
	} while (inputFile.getFilePointer() < inputFileObject.length());

	return res;
    }
}
