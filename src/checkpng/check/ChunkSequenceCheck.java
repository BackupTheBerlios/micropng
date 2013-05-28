package checkpng.check;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.FileData;
import micropng.chunkview.chunk.Type;
import micropng.fileview.PNGProperties;

public class ChunkSequenceCheck extends Check {
    public ChunkSequenceCheck(CheckTask task) {
	super(task);
	// TODO Auto-generated constructor stub
    }

    private Chunk readChunk(RandomAccessFile input, File inputFile) throws IOException {
	final int length = input.readInt();
	final int type;
	final FileData data;
	final int crc;
	
	if (((0xFFFFFFFFl) & length) > PNGProperties.getMaxSize()) {
	    warn("chunk size too big (" + length + ")");
	}

	type = input.readInt();

	if (!Type.isThirdByteUpperCase(type)) {
	    incompatible("chunk type \"" + Type.valueOf(type) + "\" does not conform to standard (third byte uppercase)");
	}
	if (!Type.isKnown(type) && !Type.isAncillary(type)) {
	    incompatible("chunk type \"" + Type.valueOf(type) + "\" is unknown and not ancillary");
	}

	data = new FileData(inputFile, input.getFilePointer(), length);

	input.seek(input.getFilePointer() + length);
	crc = input.readInt();

	return new Chunk(type, data, crc);
    }

    public ChunkSequence readSequence(RandomAccessFile input, File inputFile) throws IOException {
	final ChunkSequence res = new ChunkSequence();

	do {
	    res.add(readChunk(input, inputFile));
	} while (input.getFilePointer() < inputFile.length());

	return res;
    }
}
