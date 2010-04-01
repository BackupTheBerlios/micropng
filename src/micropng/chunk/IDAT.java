package micropng.chunk;

import micropng.pngoptimization.Optimizer;
import micropng.pngoptimization.OptimizerOrdering;
import micropng.pngordering.DataComparatorPosition;

public class IDAT {
    public IDAT(Optimizer optimizer) {
	Type type = new Type("IDAT");
	Type post = new Type("PLTE");
	int typeInt = type.toInt();
	OptimizerOrdering ordering = optimizer.getOptimizerOrdering();
	ordering.addPartialRelation(type, post);
	ordering.addDataComparator(typeInt, new DataComparatorPosition());
    }
}
