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

    private File inputFileObject;
    private File outputFileObject;
    private Configuration configuration;

    public Optimizer(Configuration configuration) {
	this.configuration = configuration;
    }

    public void run() throws IOException {
	inputFileObject = new File(configuration.getFilename());
	FileReader reader = new FileReader();
	LinkedList<Chunk> chunkSequence = reader.readSequence(inputFileObject);

	outputFileObject = new File(inputFileObject.getName() + "_output.png");
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
