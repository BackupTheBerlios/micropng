package micropng;

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

    public LinkedList<Chunk> readSequence(RandomAccessFile inputFile) throws IOException {
	LinkedList<Chunk> res = new LinkedList<Chunk>();
	long filePointerPosition = PNGProperties.getSignature().length;

	inputFile.seek(filePointerPosition);

	do {
	    int length = inputFile.readInt();
	    Type type = new Type(inputFile.readInt());
	    filePointerPosition += 8;

	    Data data = new FileData(inputFile, inputFile.getFilePointer(), length);
	    filePointerPosition += length;
	    inputFile.seek(filePointerPosition);

	    int crc = inputFile.readInt();
	    filePointerPosition += 4;

	    res.add(new Chunk(type, data, crc));
	} while (inputFile.getFilePointer() < inputFile.length());

	return res;
    }
}
