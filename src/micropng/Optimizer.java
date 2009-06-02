package micropng;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.LinkedList;

import micropng.chunk.Chunk;

public class Optimizer {

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
	File inputFileObject = new File(inputFilename);
	RandomAccessFile inputFile = new RandomAccessFile(inputFileObject, "r");

	FileReader reader = new FileReader();
	LinkedList<Chunk> chunkSequence = reader.readSequence(inputFileObject);

	File outputFileObject = new File(outputFilename);
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
    }
}
