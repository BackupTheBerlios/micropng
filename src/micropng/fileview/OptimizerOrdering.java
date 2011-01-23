package micropng.fileview;

import java.util.Collections;

import micropng.chunkview.chunk.OrganisationSequence;

public class OptimizerOrdering {
    public void optimize(OrganisationSequence seq) {
	Collections.sort(seq);
    }
}
