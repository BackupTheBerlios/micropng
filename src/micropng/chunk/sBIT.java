package micropng.chunk;

import micropng.pngoptimization.Optimizer;
import micropng.pngoptimization.OptimizerOrdering;

public class sBIT {
    public sBIT(Optimizer optimizer) {
	Type type = new Type("sBIT");
	Type post = new Type("IHDR");
	OptimizerOrdering ordering = optimizer.getOptimizerOrdering();
	ordering.addPartialRelation(type, post);
    }
}
