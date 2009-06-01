package micropng;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.LinkedList;

import micropng.chunk.Chunk;

public class Optimizer {

    class ConcurrentLockException extends IOException {
	private static final long serialVersionUID = 1L;

	public ConcurrentLockException() {
	}
    }

    class CanNotCreateFileException extends IOException {
	private static final long serialVersionUID = 1L;

	public CanNotCreateFileException() {
	}
    }

    private String inputFilename;
    private String outputFilename;

    public Optimizer(String inputFilename, String outputFilename) {
	this.inputFilename = inputFilename;
	this.outputFilename = outputFilename;
    }

    public void run() throws IOException {
	RandomAccessFile inputFile = new RandomAccessFile(inputFilename, "r");
	FileChannel inputFileChannel = inputFile.getChannel();
	FileLock inputFileLock = inputFileChannel.tryLock(0, Long.MAX_VALUE, true);

	FileReader reader = new FileReader();
	LinkedList<Chunk> chunkSequence = reader.readSequence(inputFile);
	
	File outputFileObject = new File(outputFilename);
	RandomAccessFile outputFile;
	FileChannel outputFileChannel;
	FileLock outputFileLock;

	if (inputFileLock == null) {
	    throw new ConcurrentLockException();
	}

	if (!outputFileObject.createNewFile()) {
	    throw new CanNotCreateFileException();
	}

	outputFile = new RandomAccessFile(outputFileObject, "rw");
	outputFileChannel = outputFile.getChannel();
	outputFileLock = outputFileChannel.tryLock();

	if (outputFileLock == null) {
	    throw new ConcurrentLockException();
	}
    }
}
