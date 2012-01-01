package micropng.encodingview;

import micropng.micropng.Dimensions;

public class NoneDeInterlacer extends DeInterlacer {

    private class WorkerThread implements Runnable {

	private long width;
	private long height;
	private Filter filter;

	public WorkerThread(Dimensions size, Filter filter) {
	    this.width = size.getWidth();
	    this.height = size.getHeight();
	    this.filter = filter;
	}

	@Override
	public void run() {
	    try {
		filter.init(width);
		filter.unfilter(height);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

    }

    public void deInterlace(Dimensions size, Filter filter) throws InterruptedException {
	new Thread(new WorkerThread(size, filter)).start();
    }
}
