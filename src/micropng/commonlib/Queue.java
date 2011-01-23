package micropng.commonlib;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

public class Queue {

    private ArrayBlockingQueue<int[]> queue;
    private int[] inBlock;
    private int[] outBlock;
    private int blockSize;
    private boolean closed;

    private int inPos;
    private int outPos;

    public Queue() {
	queue = new ArrayBlockingQueue<int[]>(2);
	blockSize = 0x1 << 10;
	closed = false;
	inPos = 0;
	outPos = 0;
    }

    /**
     * 
     * @return the next value in stream or -1 when nothing is left
     * @throws InterruptedException
     */
    public int take() throws InterruptedException {
	int res;

	if (outBlock == null) {
	    synchronized (this) {
		if (queue.peek() == null) {
		    if (closed) {
			return -1;
		    } else {
			wait();
			// is queue.peek() == null check here really
			// necessary?
			if (queue.peek() == null && closed) {
			    return -1;
			}
		    }
		}
	    }

	    outBlock = queue.take();
	    outPos = 0;
	}

	res = outBlock[outPos];
	outPos++;

	if (outPos == outBlock.length) {
	    outBlock = null;
	}

	return res;
    }

    public void put(int value) throws InterruptedException {

	if (inBlock == null) {
	    inBlock = new int[blockSize];
	    inPos = 0;
	}

	inBlock[inPos] = value;
	inPos++;

	if (inPos == blockSize) {
	    synchronized (this) {
		queue.put(inBlock);
		inBlock = null;
		notify();
	    }
	}
    }

    public void close() throws InterruptedException {

	synchronized (this) {
	    if (inBlock != null) {
		int[] lastBlock = Arrays.copyOf(inBlock, inPos);
		queue.put(lastBlock);
	    }

	    closed = true;
	    notify();
	}
    }
}
