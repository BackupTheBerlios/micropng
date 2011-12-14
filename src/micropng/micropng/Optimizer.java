package micropng.micropng;

import java.io.File;
import java.io.IOException;

import micropng.chunkview.chunk.OrganisationSequence;
import micropng.fileview.OptimizerChunkAggregation;
import micropng.fileview.OptimizerOrdering;
import micropng.pngio.FileWriter;
import micropng.userinterface.UserConfiguration;

public class Optimizer {

    private UserConfiguration configuration;
    private OptimizerOrdering ordering;
    private OptimizerChunkAggregation aggregation;
    private File outputFileObject;
    private boolean configurationIsSane;

    public Optimizer(UserConfiguration userConfiguration) throws IOException {
	this.configuration = userConfiguration;

	this.ordering = new OptimizerOrdering();
	this.aggregation = new OptimizerChunkAggregation();
    }

    public void run() throws IOException {
	if (!configurationIsSane) {
	    return;
	}
	//OrganisationSequence chunkOrganisationSequence = new OrganisationSequence(configuration.getChunkSequence());

	//ordering.optimize(chunkOrganisationSequence);
	//aggregation.optimize(chunkOrganisationSequence);

	//outputFileObject = new File(configuration.getPath() + "_output.png");
	FileWriter writer = new FileWriter();
	//writer.writeSequence(outputFileObject, chunkOrganisationSequence.toChunkSequence());
    }
}
