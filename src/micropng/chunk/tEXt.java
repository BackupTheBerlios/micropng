package micropng.chunk;

import micropng.DataComparatorAlphabet;
import micropng.Optimizer;
import micropng.OptimizerOrdering;

public class tEXt {
    public tEXt(Optimizer optimizer) {
	Type type = new Type("tEXt");
	Type post = new Type("IHDR");
	int typeInt = type.toInt();
	OptimizerOrdering ordering = optimizer.getOptimizerOrdering();
	ordering.addPartialRelation(type, post);
	ordering.addDataComparator(typeInt, new DataComparatorAlphabet());
    }
}
