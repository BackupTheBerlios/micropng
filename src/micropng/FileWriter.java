package micropng;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import micropng.chunk.Chunk;

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

	for(Chunk c : chunkSequence) {
	    outputFile.write(c.getDataSizeByteArray());
	    outputFile.write(c.getTypeByteArray());
	    outputFile.write(c.getData().getArray());
	    outputFile.write(c.getCrcByteArray());
	}
    }
}
