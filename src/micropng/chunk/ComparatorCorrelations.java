package micropng.chunk;

public enum ComparatorCorrelations {

    bKGD(null),
    cHRM(null),
    gAMA(null),
    hIST(null),
    iCCP(null),
    IHDR(null),
//    IDAT(SameTypeComparator.KEEP_INPUT_ORDERING),
    IDAT(null),
    IEND(null),
    iTXt(SameTypeComparator.ALPHABETICAL_ORDERING),
    pHYs(null),
    PLTE(null),
    sBIT(null),
    sPLT(SameTypeComparator.ALPHABETICAL_ORDERING),
    sRGB(null),
    tEXt(SameTypeComparator.ALPHABETICAL_ORDERING),
    tIME(null),
    tRNS(null),
    zTXt(SameTypeComparator.ALPHABETICAL_ORDERING);

    private final SameTypeComparator comparator;

    private ComparatorCorrelations(SameTypeComparator comparator) {
	this.comparator = comparator;
    }

    public SameTypeComparator getComparator() {
	return comparator;
    }
}
