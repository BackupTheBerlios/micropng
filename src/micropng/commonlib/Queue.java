package micropng.commonlib;

import java.util.ArrayList;
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
    private int remainingBitsInByte;
    private int currentByte;

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
		while (queue.peek() == null) {
		    if (closed) {
			return -1;
		    }
		    wait();
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

    public int takeBit() throws InterruptedException {
	if (remainingBitsInByte == 0) {
	    currentByte = take();
	    remainingBitsInByte = 7;
	} else {
	    currentByte >>= 1;
	    remainingBitsInByte--;
	}

	return currentByte & 0x01;
    }

    public int takeBits(int numberOfBits) throws InterruptedException {
	int res = 0;
	for (int i = 0; i < numberOfBits; i++) {
	    res <<= 1;
	    res |= takeBit();
	}
	return res;
    }

    public ArrayList<Integer> getRemainingBitsOfCurrentByte() throws InterruptedException {
	ArrayList<Integer> res = new ArrayList<Integer>(remainingBitsInByte);

	while (remainingBitsInByte > 0) {
	    currentByte >>= 1;
	    res.add(currentByte & 0x01);
	    remainingBitsInByte--;
	}

	return res;
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
