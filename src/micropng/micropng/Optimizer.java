package micropng.micropng;

import java.io.File;
import java.io.IOException;

import micropng.chunkview.chunk.OrganisationSequence;
import micropng.fileview.OptimizerChunkAggregation;
import micropng.fileview.OptimizerOrdering;
import micropng.pngio.FileReader;
import micropng.pngio.FileWriter;

public class Optimizer {

    private Configuration configuration;
    private OptimizerOrdering ordering;
    private OptimizerChunkAggregation aggregation;
    private File inputFileObject;
    private File outputFileObject;

    public Optimizer(Configuration userConfiguration) {
	Configurator configurator = new Configurator();
	this.configuration = configurator.sanitize(userConfiguration);
	this.ordering = new OptimizerOrdering();
	this.aggregation = new OptimizerChunkAggregation();
    }

    public void run() throws IOException {
	inputFileObject = new File(configuration.getPath());
	FileReader reader = new FileReader();
	OrganisationSequence chunkOrganisationSequence = new OrganisationSequence(reader.readSequence(inputFileObject));

	ordering.optimize(chunkOrganisationSequence);
	aggregation.optimize(chunkOrganisationSequence);

	outputFileObject = new File(configuration.getPath() + "_output.png");
	FileWriter writer = new FileWriter();
	writer.writeSequence(outputFileObject, chunkOrganisationSequence.toChunkSequence());
    }
}
