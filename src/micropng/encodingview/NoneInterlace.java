package micropng.encodingview;

import micropng.micropng.Dimensions;

public class NoneInterlace extends Interlace {
    public class NoneInterlaceThread implements Runnable {
	@Override
	public void run() {
	    int next = in();
	    while (next != -1) {
		out(next);
		next = in();
	    }
	    done();
	}
    }

    private Dimensions[] graphicsSizes;

    public NoneInterlace(Dimensions size) {
	this.graphicsSizes = new Dimensions[] { size };
    }

    public Dimensions[] getGraphicsSizes() {
	return graphicsSizes;
    }

    public void start() {
	new Thread(new NoneInterlaceThread()).start();
    }
}
