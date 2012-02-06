package micropng.commonlib;

public class Sink extends StreamFilter {
    private class SinkThread implements Runnable {

	@Override
	public void run() {
	    int in;

	    do {
		in = in();
		// System.out.format("%02x ", in);
	    } while (in != -1);
	}
    }

    @Override
    public void start() {
	new Thread(new SinkThread()).start();
    }
}
