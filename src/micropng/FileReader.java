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
	RandomAccessFile inputFile = new RandomAccessFile(inputFileObject, "r");
	long filePointerPosition = PNGProperties.getSignature().length;
	inputFile.seek(filePointerPosition);

	do {
	    int length;
	    Type type;
	    Data data;
	    int crc;

	    length = inputFile.readInt();
	    type = new Type(inputFile.readInt());
	    data = new FileData(inputFile, inputFile.getFilePointer(), length);
	    inputFile.seek(inputFile.getFilePointer() + length);
	    crc = inputFile.readInt();

	    if (inputFile.getChannel().lock(filePointerPosition, length + 12, true) == null) {
		throw new ConcurrentLockException();
	    }

	    res.add(new Chunk(type, data, crc));
	    filePointerPosition = inputFile.getFilePointer();
	} while (inputFile.getFilePointer() < inputFileObject.length());

	return res;
    }
}
