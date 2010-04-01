package micropng.chunk;

import micropng.pngoptimization.Optimizer;
import micropng.pngoptimization.OptimizerOrdering;

public class hIST {
    public hIST(Optimizer optimizer) {
	Type type = new Type("hIST");
	Type post = new Type("PLTE");
	OptimizerOrdering ordering = optimizer.getOptimizerOrdering();
	ordering.addPartialRelation(type, post);
    }
}
