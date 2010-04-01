package micropng.pngordering;

public class DataComparatorPosition implements DataComparator {

    @Override
    public int compare(OrderingKey a, OrderingKey b) {
	if (a.getPosition() < b.getPosition()) {
	    return -1;
	} else {
	    return 1;
	}
    }
}
