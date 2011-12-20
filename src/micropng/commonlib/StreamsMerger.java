package micropng.commonlib;

import java.util.LinkedList;

import micropng.micropng.MicropngThread;

public class StreamsMerger extends StreamFilter {

    private class WorkerThread implements MicropngThread {

	private StreamFilter currentStream;

	@Override
	public void run() {
	    boolean maybeMoreWork = true;
	    int value;

	    try {
		while (maybeMoreWork) {
		    synchronized (workerThread) {
			if (buffer.peek() == null) {
			    if (done) {
				maybeMoreWork = false;
			    } else {
				workerThread.wait();
			    }
			} else {
			    currentStream = buffer.poll();
			    value = currentStream.in();

			    while (value != -1) {
				out(value);
				value = currentStream.in();
			    }
			}
		    }
		}
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    private Thread workerThread;
    private LinkedList<StreamFilter> buffer;
    private boolean done;

    public StreamsMerger() {
	workerThread = new Thread(new WorkerThread());
	buffer = new LinkedList<StreamFilter>();
	done = false;
	workerThread.run();
    }

    public void add(StreamFilter stream) {
	synchronized (workerThread) {
	    buffer.addLast(stream);
	    workerThread.notify();
	}
    }

    @Override
    public void done() throws InterruptedException {
	synchronized (workerThread) {
	    done = true;
	    workerThread.notify();
	}
	workerThread.join();
	super.done();
    }
}
