package micropng.pngio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import micropng.ChunkSequence;
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

	for (Chunk c : chunkSequence) {
	    outputFile.write(intToByteArray(c.getDataSize()));
	    outputFile.write(intToByteArray(c.getType()));
	    outputFile.write(c.getData().getArray());
	    outputFile.write(intToByteArray(c.getCrc()));
	}
    }

    private byte[] intToByteArray(int integer) {
	byte[] res = new byte[4];
	int tmp = integer;
	for (int i = 0; i < 4; i++) {
	    res[3 - i] = (byte) (tmp & 0xff);
	    tmp >>= 8;
	}
	return res;
    }
}
