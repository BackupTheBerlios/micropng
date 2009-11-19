package micropng.chunk;

import micropng.Optimizer;
import micropng.OptimizerOrdering;

public class hIST {
    public hIST(Optimizer optimizer) {
	Type type = new Type("hIST");
	Type post = new Type("PLTE");
	OptimizerOrdering ordering = optimizer.getOptimizerOrdering();
	ordering.addPartialRelation(type, post);
    }
}
