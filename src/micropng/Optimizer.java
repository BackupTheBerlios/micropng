package micropng;

import java.io.File;
import java.io.IOException;

import micropng.chunk.ChunkRegistrar;

public class Optimizer {

    private File inputFileObject;
    private File outputFileObject;
    private Configuration configuration;
    private OptimizerOrdering ordering;

    public Optimizer(Configuration configuration) {
	this.configuration = configuration;
	this.ordering = new OptimizerOrdering();
    }

    public OptimizerOrdering getOptimizerOrdering() {
	return ordering;
    }

    public void run() throws IOException {
	inputFileObject = new File(configuration.getFilename());
	FileReader reader = new FileReader();
	ChunkSequence chunkSequence = reader.readSequence(inputFileObject);

	new ChunkRegistrar(this);

	chunkSequence = ordering.optimize(chunkSequence);

	outputFileObject = new File(inputFileObject.getName() + "_output.png");
	FileWriter writer = new FileWriter();
	writer.writeSequence(outputFileObject, chunkSequence);
    }
}
