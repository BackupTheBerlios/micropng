package micropng.chunk;

import java.io.IOException;
import java.io.RandomAccessFile;

public class ChunkFactory {

    public ChunkFactory() {
    }

    public Chunk readChunk(RandomAccessFile inputFile) throws IOException {
	int length = inputFile.readInt();
	Type type = new Type(inputFile.readInt());
	Data data = new FileData(inputFile, inputFile.getFilePointer(), length);
	int crc;

	inputFile.seek(inputFile.getFilePointer() + length);
	crc = inputFile.readInt();

	return new Chunk(type, data, crc);
    }
}
