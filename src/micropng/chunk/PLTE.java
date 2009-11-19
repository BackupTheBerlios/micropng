package micropng.chunk;

import micropng.Optimizer;
import micropng.OptimizerOrdering;

public class PLTE {
    public PLTE(Optimizer optimizer) {
	Type type = new Type("PLTE");
	Type post = new Type("IHDR");
	OptimizerOrdering ordering = optimizer.getOptimizerOrdering();
	ordering.addPartialRelation(type, post);
    }
}
