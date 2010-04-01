package micropng.chunk;

import micropng.pngoptimization.Optimizer;
import micropng.pngoptimization.OptimizerOrdering;
import micropng.pngordering.DataComparatorAlphabet;

public class iTXT {
    public iTXT(Optimizer optimizer) {
	Type type = new Type("iTXT");
	Type post = new Type("IHDR");
	int typeInt = type.toInt();
	OptimizerOrdering ordering = optimizer.getOptimizerOrdering();
	ordering.addPartialRelation(type, post);
	ordering.addDataComparator(typeInt, new DataComparatorAlphabet());
    }
}
