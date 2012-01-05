package micropng.encodingview;

import micropng.micropng.Dimensions;

public class Adam7DecoderFilterController extends DeInterlacerFilterController {
    private class FilterControllerThread implements Runnable {
	@Override
	public void run() {
	    Dimensions[] graphicsSizes = Adam7Interlace.calculate(size);
	    for (int i = 0; i < graphicsSizes.length; i++) {
		if (!graphicsSizes[i].isEmpty()) {
		    filter.init(graphicsSizes[i].getWidth());
		    filter.unfilter(graphicsSizes[i].getHeight());
		}
	    }
	}
    }

    private Dimensions size;
    private Filter filter;

    public Adam7DecoderFilterController(Dimensions size, Filter filter) {
	this.size = size;
	this.filter = filter;
    }

    @Override
    public void start() {
	new Thread(new FilterControllerThread()).start();
    }
}
