package micropng.micropng;

import java.io.IOException;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.OptimizerRemoveUselessSBIT;
import micropng.chunkview.chunk.OrganisationSequence;
import micropng.fileview.OptimizerChunkAggregation;
import micropng.fileview.OptimizerOrdering;
import micropng.userinterface.InternalConfiguration;

public class Optimizer {

    public ChunkSequence optimize(InternalConfiguration configuration) throws IOException {
	ChunkSequence chunkSequence = configuration.getChunkSequence();

	if (!configuration.unknownMandatoryChunk()) {
	    if (configuration.sortChunks()) {
		OrganisationSequence chunkOrganisationSequence = new OrganisationSequence(
			chunkSequence);
		OptimizerOrdering ordering = new OptimizerOrdering();
		ordering.optimize(chunkOrganisationSequence);
		chunkSequence = chunkOrganisationSequence.toChunkSequence();
	    }

	    if (configuration.removeUselessSBIT()) {
		OptimizerRemoveUselessSBIT sBITRemoval = new OptimizerRemoveUselessSBIT();
		sBITRemoval.optimize(chunkSequence);
	    }

	    if (configuration.regroupIDATChunks()) {
		OrganisationSequence chunkOrganisationSequence = new OrganisationSequence(
			chunkSequence);
		OptimizerChunkAggregation aggregation = new OptimizerChunkAggregation();
		aggregation.optimize(chunkOrganisationSequence);
		chunkSequence = chunkOrganisationSequence.toChunkSequence();
	    }
	}

	// outputFileObject = new File(configuration.getPath() + "_output.png");
	//FileWriter writer = new FileWriter();
	//writer.writeSequence(outputFileObject, chunkSequence);
	return chunkSequence;
    }
}
