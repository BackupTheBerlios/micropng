package micropng.encodingview;

import micropng.micropng.MicropngThread;

public class NoneDeInterlacer implements DeInterlacer {

    private class WorkerThread implements MicropngThread {

	public WorkerThread(long width, long height, Filter filter) {
	    super();
	    this.width = width;
	    this.height = height;
	    this.filter = filter;
	}


	private long width;
	private long height;
	private Filter filter;

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

    public NoneDeInterlacer() {

    }

    @Override
    public void deInterlace(long width, long height, Filter filter) throws InterruptedException {
	new Thread(new WorkerThread(width, height, filter)).run();
    }

}
