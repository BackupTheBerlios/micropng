package micropng.commonlib;

public class DummyFilter extends StreamFilter {

    private class DummyFilterThread implements Runnable {

	@Override
	public void run() {
	    try {
		int next = in();
		while (next != -1) {
		    out(next);
		    next = in();
		}
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
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
