package micropng;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import micropng.chunk.Chunk;

public class Optimizer {

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
	FileWriter writer = new FileWriter();
	writer.writeSequence(outputFileObject, chunkSequence);
    }
}
