package micropng.chunk;

import micropng.Optimizer;
import micropng.OptimizerOrdering;

public class bKGD {
    public bKGD(Optimizer optimizer) {
	Type type = new Type("bKGD");
	Type post = new Type("PLTE");
	OptimizerOrdering ordering = optimizer.getOptimizerOrdering();
	ordering.addPartialRelation(type, post);
    }
}
