package micropng.encodingview;

import micropng.commonlib.BigArrayOfInt;
import micropng.commonlib.StreamFilter;
import micropng.micropng.CodecInfo;
import micropng.micropng.Dimensions;

public class Filter extends StreamFilter {

    private class FilterThread implements Runnable {
	@Override
	public void run() {
	    int bitDepth = codecInfo.getBitDepth();

	    for (Dimensions dimension : dimensions) {
		if (!dimension.isEmpty()) {
		    long height = dimension.getHeight();
		    long bitsPerScanline = bitDepth * dimension.getWidth();
		    long newScanLineSize = (long) Math.ceil(bitsPerScanline / 8f);

		    for (int i = 0; i < lastScanline.length; i++) {
			lastScanline[i] = new BigArrayOfInt(newScanLineSize);
		    }

		    for (long i = 0; i < height; i++) {
			FilterType filterType = FilterType.getType(in());

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
	    }
	    done();
	}
    }

    private enum FilterType {
	NONE, SUB, UP, AVERAGE, PAETH;
	public static FilterType getType(int i) {
	    return FilterType.values()[i];
	}
    }

    private final static int BYTE_MASK = 0x0ff;
    private CodecInfo codecInfo;
    private BigArrayOfInt[] lastScanline;
    private Dimensions[] dimensions;

    public Filter(CodecInfo codecInfo) {
	this.codecInfo = codecInfo;
	lastScanline = new BigArrayOfInt[codecInfo.numberOfChannels()];
    }

    public void start(Dimensions[] dimensions) {
	this.dimensions = dimensions;
	new Thread(new FilterThread()).start();
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
