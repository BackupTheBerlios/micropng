package micropng;

import java.io.IOException;
import java.io.RandomAccessFile;

public class FileReader {

    public FileReader() {
    }

    public ChunkList readSequence(RandomAccessFile inputFile)
	    throws IOException {
	ChunkList res = new ChunkList();

	inputFile.seek(PNGProperties.getSignature().length);

	return null;
    }
}
