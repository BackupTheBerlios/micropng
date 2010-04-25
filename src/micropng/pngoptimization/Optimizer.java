package micropng.pngoptimization;

import java.io.File;
import java.io.IOException;

import micropng.Configuration;
import micropng.chunk.OrganisationSequence;
import micropng.pngio.FileReader;
import micropng.pngio.FileWriter;

public class Optimizer {

    private Configuration configuration;
    private OptimizerOrdering ordering;
    private OptimizerChunkAggregation aggregation;
    private File inputFileObject;
    private File outputFileObject;

    public Optimizer(Configuration configuration) {
	this.configuration = configuration;
	this.ordering = new OptimizerOrdering();
	this.aggregation = new OptimizerChunkAggregation();
    }

    public OptimizerOrdering getOptimizerOrdering() {
	return ordering;
    }

    public void run() throws IOException {
	inputFileObject = new File(configuration.getFilename());
	FileReader reader = new FileReader();
	OrganisationSequence chunkOrganisationSequence = new OrganisationSequence(reader.readSequence(inputFileObject));

	ordering.optimize(chunkOrganisationSequence);
	aggregation.optimize(chunkOrganisationSequence);

	outputFileObject = new File(configuration.getFilename() + "_output.png");
	FileWriter writer = new FileWriter();
	writer.writeSequence(outputFileObject, chunkOrganisationSequence.toChunkSequence());
    }
}
