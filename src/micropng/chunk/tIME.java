package micropng.chunk;

import micropng.Optimizer;
import micropng.OptimizerOrdering;

public class tIME {
    public tIME(Optimizer optimizer) {
	Type type = new Type("tIME");
	Type post = new Type("IHDR");
	OptimizerOrdering ordering = optimizer.getOptimizerOrdering();
	ordering.addPartialRelation(type, post);
    }
}
