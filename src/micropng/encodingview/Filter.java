package micropng.encodingview;

import micropng.commonlib.BigArrayOfInt;
import micropng.commonlib.StreamFilter;

public class Filter extends StreamFilter {

    private enum FilterTypes {
	NONE, SUB, UP, AVERAGE, PAETH;
	public static FilterTypes getType(int i) {
	    return FilterTypes.values()[i];
	}
    }

    private final static int BYTE_MASK = 0x0ff;
    private BigArrayOfInt[] lastScanline;
    private FilterTypes filterType;
    private int numberOfChannels;
    private int bitsPerSample;

    public Filter(int numberOfChannels, int bitsPerSample) {
	this.bitsPerSample = bitsPerSample;
	this.numberOfChannels = numberOfChannels;
	lastScanline = new BigArrayOfInt[numberOfChannels];
    }

    public void init(long newImageWidth) {
	long bitsPerScanline = newImageWidth * bitsPerSample;
	long newScanLineSize = (long) Math.ceil(bitsPerScanline / 8f);
	for (int i = 0; i < numberOfChannels; i++) {
	    lastScanline[i] = new BigArrayOfInt(newScanLineSize);
	}
    }

    public void unfilter(long numberOfLines) {
	for (long i = 0; i < numberOfLines; i++) {
	    filterType = FilterTypes.getType(in());

	    switch (filterType) {
	    case NONE:
		doNone();
		break;
	    case SUB:
		doSub();
		break;
	    case UP:
		doUp();
		break;
	    case AVERAGE:
		doAverage();
		break;
	    case PAETH:
		doPaeth();
		break;
	    }
	}
    }

    private void doNone() {
	for (long i = 0; i < lastScanline[0].size; i++) {
	    for (BigArrayOfInt channel : lastScanline) {
		int currentValue = in();
		channel.set(i, currentValue);
		out(currentValue);
	    }
	}
    }

    private void doSub() {
	int[] currentLinelastValues = new int[lastScanline.length];
	for (long i = 0; i < lastScanline[0].size; i++) {
	    for (int j = 0; j < lastScanline.length; j++) {
		BigArrayOfInt lastLineCurrentChannel = lastScanline[j];
		int lastValue = currentLinelastValues[j];
		int currentValue = (in() + lastValue) & BYTE_MASK; 
		out(currentValue);
		currentLinelastValues[j] = currentValue;
		lastLineCurrentChannel.set(i, currentValue);
	    }
	}
    }

    private void doUp() {
	for (long i = 0; i < lastScanline[0].size; i++) {
	    for (BigArrayOfInt lastLineCurrentChannel : lastScanline) {
		int currentValue = (in() + lastLineCurrentChannel.elementAt(i)) & BYTE_MASK;
		out(currentValue);
		lastLineCurrentChannel.set(i, currentValue);
	    }
	}
    }

    private void doAverage() {
	int[] currentLineLastValues = new int[lastScanline.length];

	for (long i = 0; i < lastScanline[0].size; i++) {
	    for (int j = 0; j < lastScanline.length; j++) {
		BigArrayOfInt lastLineCurrentChannel = lastScanline[j];
		int currentLineLastValue = currentLineLastValues[j];
		int addend = (lastLineCurrentChannel.elementAt(i) + currentLineLastValue) >> 1;
	    	int currentValue = (in() + addend) & BYTE_MASK;
		out(currentValue);
		currentLineLastValues[j] = currentValue;
		lastLineCurrentChannel.set(i, currentValue);
	    }
	}
    }

    private void doPaeth() {
	int[] currentLineLastValues = new int[lastScanline.length];
	int[] lastValuesAbove = new int[lastScanline.length];
	
	for (long i = 0; i < lastScanline[0].size; i++) {
	    for (int j = 0; j < lastScanline.length; j++) {
		BigArrayOfInt lastLineCurrentChannel = lastScanline[j];
		int last = currentLineLastValues[j];
		int lastAbove = lastValuesAbove[j];
		int above = lastLineCurrentChannel.elementAt(i);
		int addend = paethPredictor(last, above, lastAbove);
		int currentValue = (in() + addend) & BYTE_MASK;
		out(currentValue);
		currentLineLastValues[j] = currentValue;
		lastValuesAbove[j] = above;
		lastLineCurrentChannel.set(i, currentValue);
	    }
	}
    }

    private int paethPredictor(int a, int b, int c) {
	int Pr;
	int p = a + b - c;
	int pa = Math.abs(p - a);
	int pb = Math.abs(p - b);
	int pc = Math.abs(p - c);
	if (pa <= pb && pa <= pc) {
	    Pr = a;
	} else if (pb <= pc) {
	    Pr = b;
	} else {
	    Pr = c;
	}
	return Pr;
    }
}
