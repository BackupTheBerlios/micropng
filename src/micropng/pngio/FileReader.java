package micropng.pngio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.FileData;
import micropng.fileview.PNGProperties;

public class FileReader {

    private Chunk readChunk(RandomAccessFile input) throws IOException {
	int length = input.readInt();
	int type = input.readInt();
	FileData data = new FileData(input, input.getFilePointer(), length);
	int crc;

	input.seek(input.getFilePointer() + length);
	crc = input.readInt();

	return new Chunk(type, data, crc);
    }

    public ChunkSequence readSequence(File inputFile) throws FileNotFoundException, IOException {
	ChunkSequence res = new ChunkSequence();
	RandomAccessFile input = new RandomAccessFile(inputFile, "r");

	input.seek(PNGProperties.getSignature().length);
	do {
	    Chunk nextChunk = readChunk(input);
	    res.add(nextChunk);
	} while (input.getFilePointer() < inputFile.length());

	return res;
    }
}
