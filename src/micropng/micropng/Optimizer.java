package micropng.micropng;

import java.io.File;
import java.io.IOException;

import micropng.chunkview.chunk.OrganisationSequence;
import micropng.fileview.OptimizerChunkAggregation;
import micropng.fileview.OptimizerOrdering;
import micropng.pngio.FileWriter;
import micropng.userinterface.InternalConfiguration;

public class Optimizer {
    private InternalConfiguration configuration;
    private File outputFileObject;

    public Optimizer(InternalConfiguration configuration) throws IOException {
	this.configuration = configuration;
    }

    public void run() throws IOException {
	OrganisationSequence chunkOrganisationSequence = new OrganisationSequence(configuration.getChunkSequence());
	OptimizerOrdering ordering;
	OptimizerChunkAggregation aggregation;

	ordering = new OptimizerOrdering();
	aggregation = new OptimizerChunkAggregation();
	
	ordering.optimize(chunkOrganisationSequence);
	aggregation.optimize(chunkOrganisationSequence);

	//outputFileObject = new File(configuration.getPath() + "_output.png");
	FileWriter writer = new FileWriter();
	writer.writeSequence(outputFileObject, chunkOrganisationSequence.toChunkSequence());
    }
}
