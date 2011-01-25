package micropng.pngio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.Data;
import micropng.chunkview.chunk.FileData;
import micropng.fileview.PNGProperties;

public class FileReader {

    private File inputFile;
    private RandomAccessFile input;

    public FileReader(File inputFile) throws FileNotFoundException {
	this.inputFile = inputFile;
	input = new RandomAccessFile(inputFile, "r");
    }

    private Chunk readChunk() throws IOException {
	int length = input.readInt();
	int type = input.readInt();
	Data data = new FileData(input, input.getFilePointer(), length);
	int crc;

	input.seek(input.getFilePointer() + length);
	crc = input.readInt();

	return new Chunk(type, data, crc);
    }

    public ChunkSequence readSequence() throws IOException {
	ChunkSequence res = new ChunkSequence();

	input.seek(PNGProperties.getSignature().length);
	do {
	    Chunk nextChunk = readChunk();
	    res.add(nextChunk);
	} while (input.getFilePointer() < inputFile.length());

	return res;
    }
}
