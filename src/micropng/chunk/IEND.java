package micropng.chunk;

import micropng.Optimizer;
import micropng.OptimizerOrdering;

public class IEND {
    public IEND(Optimizer optimizer) {
	Type type = new Type("IEND");
	Type post = new Type("IDAT");
	OptimizerOrdering ordering = optimizer.getOptimizerOrdering();
	ordering.addPartialRelation(type, post);
    }
}
