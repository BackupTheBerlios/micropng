package micropng.chunk;

import micropng.Optimizer;
import micropng.OptimizerOrdering;

public class iCCP {
    public iCCP(Optimizer optimizer) {
	Type type = new Type("iCCP");
	Type post = new Type("IHDR");
	OptimizerOrdering ordering = optimizer.getOptimizerOrdering();
	ordering.addPartialRelation(type, post);
    }
}
