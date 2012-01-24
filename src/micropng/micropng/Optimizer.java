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
	ChunkSequence res = configuration.getChunkSequence();

	if (!configuration.unknownMandatoryChunk()) {

	    if (configuration.sortChunks()) {
		OrganisationSequence chunkOrganisationSequence = new OrganisationSequence(res);
		OptimizerOrdering ordering = new OptimizerOrdering();
		ordering.optimize(chunkOrganisationSequence);
		res = chunkOrganisationSequence.toChunkSequence();
	    }
	    


	    if (configuration.removeUselessSBIT()) {
		OptimizerRemoveUselessSBIT sBITRemoval = new OptimizerRemoveUselessSBIT();
		sBITRemoval.optimize(res);
	    }

	    if (configuration.regroupIDATChunks()) {
		OrganisationSequence chunkOrganisationSequence = new OrganisationSequence(res);
		OptimizerChunkAggregation aggregation = new OptimizerChunkAggregation();
		aggregation.optimize(chunkOrganisationSequence);
		res = chunkOrganisationSequence.toChunkSequence();
	    }
	}

	return res;
    }
}
