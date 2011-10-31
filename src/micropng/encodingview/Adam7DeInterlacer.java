package micropng.encodingview;

import micropng.commonlib.Queue;
import micropng.micropng.MicropngThread;

public class Adam7DeInterlacer implements DeInterlacer {
    private class FilterFeederThread implements MicropngThread {

	private long width;
	private long height;
	private Filter filter;

	public FilterFeederThread(long width, long height, Filter filter) {
	    super();
	    this.width = width;
	    this.height = height;
	    this.filter = filter;
	}

	@Override
	public void run() {
	    try {
		for (int i = 0; i < horizontalStepSizes.length; i++) {
		    long currentWidth = (width + horizontalOffsets[i] - 1) / horizontalStepSizes[i];
		    long currentHeight = (height + verticalOffsets[i] - 1) / verticalStepSizes[i];
		    filter.init(currentWidth);
		    filter.unfilter(currentHeight);
		}

	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    private final static int[] horizontalStepSizes = { 8, 8, 4, 4, 2, 2, 1 };
    private final static int[] verticalStepSizes = { 8, 8, 8, 4, 4, 2, 2 };
    private final static int[] horizontalOffsets = { 0, 4, 0, 2, 0, 1, 0 };
    private final static int[] verticalOffsets = { 0, 0, 4, 0, 2, 0, 1 };

    private Queue input;
    private Queue output;

    public Adam7DeInterlacer(Queue input, Queue output) {
	this.input = input;
	this.output = output;
    }

    @Override
    public void deInterlace(long width, long height, Filter filter) {
	new Thread(new FilterFeederThread(width, height, filter)).run();

    }
}
