package micropng.chunk;

import micropng.Optimizer;
import micropng.OptimizerOrdering;

public class sRGB {
    public sRGB(Optimizer optimizer) {
	Type type = new Type("sRGB");
	Type post = new Type("IHDR");
	OptimizerOrdering ordering = optimizer.getOptimizerOrdering();
	ordering.addPartialRelation(type, post);
    }
}
