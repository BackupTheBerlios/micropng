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

    public void unfilter(long numberOfLines) throws InterruptedException {
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

    private void doNone() throws InterruptedException {
	for (long i = 0; i < lastScanline[0].size; i++) {
	    int currentValue;
	    for (BigArrayOfInt channel : lastScanline) {
		currentValue = in();
		channel.set(i, currentValue);
		out(currentValue);
	    }
	}
    }

    private void doSub() throws InterruptedException {
	for (long i = 0; i < lastScanline[0].size; i++) {
	    int currentValue = 0;
	    int lastValue;
	    for (BigArrayOfInt channel : lastScanline) {
		lastValue = currentValue;
		currentValue = (in() + lastValue) & BYTE_MASK;
		channel.set(i, currentValue);
		out(currentValue);
	    }
	}
    }

    private void doUp() throws InterruptedException {
	for (long i = 0; i < lastScanline[0].size; i++) {
	    int currentValue;
	    for (BigArrayOfInt channel : lastScanline) {
		currentValue = (in() + channel.elementAt(i)) & BYTE_MASK;
		channel.set(i, currentValue);
		out(currentValue);
	    }
	}
    }

    private void doAverage() throws InterruptedException {
	for (long i = 0; i < lastScanline[0].size; i++) {
	    int currentValue = 0;
	    int lastValue;
	    int addend;
	    for (BigArrayOfInt channel : lastScanline) {
		lastValue = currentValue;
		addend = (channel.elementAt(i) + lastValue) >> 1;
		currentValue = (in() + addend) & BYTE_MASK;
		channel.set(i, currentValue);
		out(currentValue);
	    }
	}
    }

    private void doPaeth() throws InterruptedException {
	for (long i = 0; i < lastScanline[0].size; i++) {
	    int current = 0;
	    int last;
	    int above;
	    int lastAbove = 0;
	    int addend;
	    for (BigArrayOfInt channel : lastScanline) {
		last = current;
		above = channel.elementAt(i);
		addend = paethPredictor(last, above, lastAbove);
		current = (in() + addend) & BYTE_MASK;
		channel.set(i, current);
		out(current);
		lastAbove = 0;
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
