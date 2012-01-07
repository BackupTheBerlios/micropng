package micropng.encodingview;

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

    public void start() {
	new Thread(new NoneInterlaceThread()).start();
    }
}
