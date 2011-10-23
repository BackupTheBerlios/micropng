package micropng.encodingview;

import micropng.commonlib.BigArrayOfInt;
import micropng.commonlib.Queue;

public class Filter {
    private enum FilterMode {
	NONE, SUB, UP, AVERAGE, PAETH;
	public static FilterMode getMode(int i) {
	    return FilterMode.values()[i];
	}
    }

    private final static int BYTE_MASK = 0x0ff;
    private BigArrayOfInt[] lastScanline;
    private FilterMode filterMode;
    private Queue input;
    private Queue output;
    private int byteBoundaryOffset;
    private int offsetBitMask;
    private int numberOfChannels;
    private int bitsPerSample;

    public Filter(Queue input, Queue output, int numberOfChannels, int bitsPerSample) {
	this.input = input;
	this.output = output;
	this.bitsPerSample = bitsPerSample;
	this.numberOfChannels = numberOfChannels;
	lastScanline = new BigArrayOfInt[numberOfChannels];
    }

    public void init(long newImageWidth) {
	long bitsPerScanline = newImageWidth * bitsPerSample;
	long newScanLineSize = (long) Math.ceil(bitsPerScanline / 8f);
	byteBoundaryOffset = (int) (bitsPerScanline & 0x07);
	offsetBitMask = (1 << byteBoundaryOffset) - 1;
	for (int i = 0; i < numberOfChannels; i++) {
	    lastScanline[i] = new BigArrayOfInt(newScanLineSize);
	}
    }

    public void unfilter(long numberOfLines) throws InterruptedException {
	filterMode = FilterMode.getMode(input.take());

	switch (filterMode) {
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
		currentValue = input.take();
		channel.set(i, currentValue);
		output.put(currentValue);
	    }
	}
    }

    private void doSub() throws InterruptedException {
	for (long i = 0; i < lastScanline[0].size; i++) {
	    int currentValue = 0;
	    int lastValue;
	    for (BigArrayOfInt channel : lastScanline) {
		lastValue = currentValue;
		currentValue = (input.take() + lastValue) & BYTE_MASK;
		channel.set(i, currentValue);
		output.put(currentValue);
	    }
	}
    }

    private void doUp() throws InterruptedException {
	for (long i = 0; i < lastScanline[0].size; i++) {
	    int currentValue;
	    for (BigArrayOfInt channel : lastScanline) {
		currentValue = (input.take() + channel.elementAt(i)) & BYTE_MASK;
		channel.set(i, currentValue);
		output.put(currentValue);
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
		currentValue = (input.take() + addend) & BYTE_MASK;
		channel.set(i, currentValue);
		output.put(currentValue);
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
		current = (input.take() + addend) & BYTE_MASK;
		channel.set(i, current);
		output.put(current);
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
