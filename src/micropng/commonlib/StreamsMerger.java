package micropng.commonlib;

import java.util.LinkedList;

import micropng.zlib.deflate.DataBlockHeader;

public class StreamsMerger extends StreamFilter {

    private class WorkerThread implements Runnable {

	@Override
	public void run() {

	    try {
		boolean done = false;

		while (!done) {
		    synchronized (workerThread) {
			if (buffer.peek() == null) {
			    if (last) {
				done = true;
			    } else {
				workerThread.wait();
			    }

			} else {
			    DummyFilter dummy = new DummyFilter();
			    int next = in();
			    while (next != -1) {
				out(next);
				next = in();
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

    private WorkerThread workerThread;
    private Thread threadObject;
    private boolean last;
    private LinkedList<StreamFilter> buffer;

    public StreamsMerger() {
	workerThread = new WorkerThread();
	threadObject = new Thread(workerThread);
	buffer = new LinkedList<StreamFilter>();
	last = false;
    }

    public void start() {
	threadObject.start();
    }

    public void append(DataBlockHeader dataBlockHeader, boolean last) {
	synchronized (workerThread) {
	    buffer.addLast(dataBlockHeader);
	    this.last = last;
	    workerThread.notify();
	}
    }
}
