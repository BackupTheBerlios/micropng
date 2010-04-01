package micropng.chunk;

import micropng.pngoptimization.Optimizer;
import micropng.pngoptimization.OptimizerOrdering;

public class tRNS {
    public tRNS(Optimizer optimizer) {
	Type type = new Type("tRNS");
	Type post = new Type("PLTE");
	OptimizerOrdering ordering = optimizer.getOptimizerOrdering();
	ordering.addPartialRelation(type, post);
    }
}
