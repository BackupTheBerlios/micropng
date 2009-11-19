package micropng.chunk;

import micropng.Optimizer;
import micropng.OptimizerOrdering;

public class pHYs {
    public pHYs(Optimizer optimizer) {
	Type type = new Type("pHYs");
	Type post = new Type("IHDR");
	OptimizerOrdering ordering = optimizer.getOptimizerOrdering();
	ordering.addPartialRelation(type, post);
    }
}
