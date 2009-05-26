package micropng;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

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
	File outputFile = new File(outputFilename);
	FileChannel inputFileChannel = inputFile.getChannel();
	FileLock inputFileLock = inputFileChannel.tryLock(0, Long.MAX_VALUE,
		true);
	FileLock outputFileLock;
	BufferedOutputStream outputFileStream;

	if (inputFileLock == null) {
	    throw new ConcurrentLockException();
	}

	if (!outputFile.createNewFile()) {
	    throw new CanNotCreateFileException();
	}

	outputFileLock = inputFileChannel.tryLock(0, Long.MAX_VALUE, true);
	if (outputFileLock == null) {
	    throw new ConcurrentLockException();
	}

	outputFileStream = new BufferedOutputStream(new FileOutputStream(
		outputFilename));
    }
}
