package micropng.commonlib;

public class DummyFilter extends StreamFilter {

    private class DummyFilterThread implements Runnable {

	@Override
	public void run() {
	    int next = in();
	    while (next != -1) {
		out(next);
		next = in();
	    }
	}
    }

    private DummyFilterThread dummyFilterThread;

    public DummyFilter() {
	dummyFilterThread = new DummyFilterThread();
    }

    public void start() {
	new Thread(dummyFilterThread).start();
    }
}
