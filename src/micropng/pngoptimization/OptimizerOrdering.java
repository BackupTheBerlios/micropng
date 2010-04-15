package micropng.pngoptimization;

import java.util.Collections;

import micropng.chunk.OrganisationSequence;

public class OptimizerOrdering {
    public void optimize(OrganisationSequence seq) {
	Collections.sort(seq);
    }
}
