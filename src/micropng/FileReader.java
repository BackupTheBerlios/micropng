package micropng;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

import micropng.chunk.Chunk;
import micropng.chunk.Data;
import micropng.chunk.FileData;
import micropng.chunk.Type;

public class FileReader {

    public FileReader() {
    }

    public LinkedList<Chunk> readSequence(File inputFileObject) throws IOException {
	LinkedList<Chunk> res = new LinkedList<Chunk>();
	long filePointerPosition = PNGProperties.getSignature().length;

	do {
	    RandomAccessFile inputFile = new RandomAccessFile(inputFileObject, "r");
	    int length;
	    long newFilePointerPosition = filePointerPosition;

	    inputFile.seek(newFilePointerPosition);

	    length = inputFile.readInt();
	    Type type = new Type(inputFile.readInt());
	    newFilePointerPosition += 8;

	    Data data = new FileData(inputFile, filePointerPosition, length);
	    newFilePointerPosition += length;
	    inputFile.seek(filePointerPosition);

	    int crc = inputFile.readInt();
	    newFilePointerPosition += 4;

	    if (inputFile.getChannel().lock(filePointerPosition, length + 12, true) == null) {
		throw new ConcurrentLockException();
	    }

	    res.add(new Chunk(type, data, crc));
	    filePointerPosition = newFilePointerPosition;
	} while (filePointerPosition < inputFileObject.length());

	return res;
    }
}
