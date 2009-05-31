package micropng;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

import micropng.chunk.Chunk;

public class FileReader {

    public FileReader() {
    }

    public LinkedList<Chunk> readSequence(RandomAccessFile inputFile)
	    throws IOException {
	LinkedList<Chunk> res = new LinkedList<Chunk>();

	inputFile.seek(PNGProperties.getSignature().length);

	return null;
    }
}
