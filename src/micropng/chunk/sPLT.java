package micropng.chunk;

import micropng.DataComparatorAlphabet;
import micropng.Optimizer;
import micropng.OptimizerOrdering;

public class sPLT {
    public sPLT(Optimizer optimizer) {
	Type type = new Type("sPLT");
	Type post = new Type("IHDR");
	int typeInt = type.toInt();
	OptimizerOrdering ordering = optimizer.getOptimizerOrdering();
	ordering.addPartialRelation(type, post);
	ordering.addDataComparator(typeInt, new DataComparatorAlphabet());
    }
}
