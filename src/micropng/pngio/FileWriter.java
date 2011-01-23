package micropng.pngio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.commonlib.FourByteConverter;
import micropng.fileview.PNGProperties;

public class FileWriter {

    class CanNotCreateFileException extends IOException {
	private static final long serialVersionUID = 1L;

	public CanNotCreateFileException() {
	}
    }

    public FileWriter() {
    }

    public void writeSequence(File outputFileObject, ChunkSequence chunkSequence) throws IOException {
	RandomAccessFile outputFile;
	FileChannel outputFileChannel;
	FileLock outputFileLock;

	if (!outputFileObject.createNewFile()) {
	    throw new CanNotCreateFileException();
	}

	outputFile = new RandomAccessFile(outputFileObject, "rw");
	outputFileChannel = outputFile.getChannel();
	outputFileLock = outputFileChannel.tryLock();

	if (outputFileLock == null) {
	    throw new ConcurrentLockException();
	}

	outputFile.write(PNGProperties.getSignature());

	for (Chunk c : chunkSequence) {
	    outputFile.write(FourByteConverter.byteArrayValue(c.getDataSize()));
	    outputFile.write(FourByteConverter.byteArrayValue(c.getType()));
	    outputFile.write(c.getData().getArray());
	    outputFile.write(FourByteConverter.byteArrayValue(c.getCrc()));
	}
    }
}
