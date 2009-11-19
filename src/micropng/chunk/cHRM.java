package micropng.chunk;

import micropng.Optimizer;
import micropng.OptimizerOrdering;

public class cHRM {
    public cHRM(Optimizer optimizer) {
	Type type = new Type("cHRM");
	Type post = new Type("IHDR");
	OptimizerOrdering ordering = optimizer.getOptimizerOrdering();
	ordering.addPartialRelation(type, post);
    }
}
