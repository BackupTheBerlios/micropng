package micropng.chunk;

import micropng.Optimizer;
import micropng.OptimizerOrdering;

public class tRNS {
    public tRNS(Optimizer optimizer) {
	Type type = new Type("tRNS");
	Type post = new Type("PLTE");
	OptimizerOrdering ordering = optimizer.getOptimizerOrdering();
	ordering.addPartialRelation(type, post);
    }
}
