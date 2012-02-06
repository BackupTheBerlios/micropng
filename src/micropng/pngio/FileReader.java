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

    private Chunk readChunk(RandomAccessFile input, File inputFile) throws IOException {
	final int length = input.readInt();
	final int type = input.readInt();
	final FileData data = new FileData(inputFile, input.getFilePointer(), length);
	final int crc;

	input.seek(input.getFilePointer() + length);
	crc = input.readInt();

	return new Chunk(type, data, crc);
    }

    public ChunkSequence readSequence(File inputFile) throws FileNotFoundException, IOException {
	final ChunkSequence res = new ChunkSequence();
	final RandomAccessFile input = new RandomAccessFile(inputFile, "r");

	input.seek(PNGProperties.getSignatureLength());
	do {
	    Chunk nextChunk = readChunk(input, inputFile);
	    res.add(nextChunk);
	} while (input.getFilePointer() < inputFile.length());

	return res;
    }
}
