package micropng;

import java.io.File;
import java.io.IOException;

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
	ChunkSequence chunkSequence = reader.readSequence(inputFileObject);

	outputFileObject = new File(inputFileObject.getName() + "_output.png");
	FileWriter writer = new FileWriter();
	writer.writeSequence(outputFileObject, chunkSequence);
    }
}
