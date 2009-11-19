package micropng.chunk;

import micropng.Optimizer;
import micropng.OptimizerOrdering;

public class gAMA {
    public gAMA(Optimizer optimizer) {
	Type type = new Type("gAMA");
	Type post = new Type("IHDR");
	OptimizerOrdering ordering = optimizer.getOptimizerOrdering();
	ordering.addPartialRelation(type, post);
    }
}
