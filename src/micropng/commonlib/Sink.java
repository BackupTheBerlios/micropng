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

    private SinkThread dataDumperThread = new SinkThread();

    public Sink() {
	dataDumperThread = new SinkThread();
    }

    public void start() {
	new Thread(dataDumperThread).start();
    }
}
