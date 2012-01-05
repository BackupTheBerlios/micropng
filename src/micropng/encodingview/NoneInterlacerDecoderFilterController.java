package micropng.encodingview;

import micropng.micropng.Dimensions;

public class NoneInterlacerDecoderFilterController extends DeInterlacerFilterController {
    private class WorkerThread implements Runnable {
	@Override
	public void run() {
	    filter.init(size.getWidth());
	    filter.unfilter(size.getHeight());
	}
    }

    private Dimensions size;
    private Filter filter;

    public NoneInterlacerDecoderFilterController(Dimensions size, Filter filter) {
	this.size = size;
	this.filter = filter;
    }

    public void start() {
	new Thread(new WorkerThread()).start();
    }
}
