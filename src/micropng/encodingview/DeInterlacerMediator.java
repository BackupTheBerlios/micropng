package micropng.encodingview;

import micropng.commonlib.Queue;

public class DeInterlacerMediator {
    private enum InterlaceMode {
	NONE, ADAM7;

	public static InterlaceMode getMode(int i) {
	    return InterlaceMode.values()[i];
	}
    }

    private Queue input;
    private Queue output;

    public DeInterlacerMediator(Queue input, Queue output) {
	super();
	this.input = input;
	this.output = output;
    }

    public void deInterlace(long width, long height, Filter filter, int interlaceMode) throws InterruptedException {
	InterlaceMode mode = InterlaceMode.getMode(interlaceMode);
	DeInterlacer deInterlacer = null;

	switch (mode) {
	case NONE:
	    deInterlacer = new NoneDeInterlacer();
	    break;
	case ADAM7:
	    deInterlacer = new Adam7DeInterlacer(input, output);
	    break;
	}

	deInterlacer.deInterlace(height, width, filter);
    }
}
