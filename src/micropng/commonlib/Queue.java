package micropng.commonlib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Queue {

    private ConcurrentLinkedQueue<int[]> queue;
    private int[] inBlock;
    private int[] outBlock;
    private int blockSize;
    private boolean closed;

    private int inPos;
    private int outPos;
    private int remainingBitsInByte;
    private int currentByte;

    public Queue() {
	queue = new ConcurrentLinkedQueue<int[]>();
	blockSize = 0x1 << 10;
	closed = false;
	inPos = 0;
	outPos = 0;
    }

    /**
     * Close for input: buffers will be internally transferred to output, and
     * put() may not be called any more on this Queue. Any call of put() on this
     * Queue after close() triggers undefined behaviour.
     */
    public void close() {
	flush();
	synchronized (this) {
	    closed = true;
	    notify();
	}
    }

    private void flush() {
	if (inBlock != null) {
	    int[] lastBlock = Arrays.copyOf(inBlock, inPos);
	    synchronized (this) {
		queue.add(lastBlock);
		inBlock = null;
		notify();
	    }
	}
    }

    public ArrayList<Integer> getRemainingBitsOfCurrentByte() {
	ArrayList<Integer> res = new ArrayList<Integer>(remainingBitsInByte);

	while (remainingBitsInByte > 0) {
	    currentByte >>= 1;
	    res.add(currentByte & 0x01);
	    remainingBitsInByte--;
	}

	return res;
    }

    /**
     * Append a value.
     * 
     * Other than take(), this method does <b>not</b> block, as it may allocate
     * unlimited space internally.
     * 
     * @param value
     *            The byte to append on this Queue, stored in the LSB of the
     *            primitive int.
     */
    public void put(int value) {

	if (inBlock == null) {
	    inBlock = new int[blockSize];
	    inPos = 0;
	}

	inBlock[inPos] = value;
	inPos++;

	if (inPos == blockSize) {
	    synchronized (this) {
		queue.add(inBlock);
		inBlock = null;
		notify();
	    }
	}
    }

    /**
     * Directly route any input from this Queue to target.
     * 
     * The external take/put methods are being bypassed. However, these methods
     * remain usable.
     * 
     * Note: calling close() on this Queue, does <b>not</b> automatically close
     * the target Queue.
     * 
     * @param target
     */

    public void shortCut(Queue target) {
	target.flush();
	synchronized (this) {
	    if (outBlock != null) {
		int[] currentBlock = Arrays.copyOfRange(outBlock, outPos, outBlock.length);
		target.queue.add(currentBlock);
	    }
	    target.queue.addAll(queue);
	    queue = target.queue;
	}
    }

    /**
     * Returns and removes the next value waiting in this queue.
     * 
     * If there is no value waiting, block until a new value is available or the
     * stream is closed.
     * 
     * @return the next value in stream or -1 when nothing is left
     */
    public int take() {
	int res;

	while (outBlock == null) {
	    synchronized (this) {
		outBlock = queue.poll();
		if (outBlock == null) {
		    if (closed) {
			return -1;
		    } else {
			try {
			    wait();
			} catch (InterruptedException e) {
			    // swallow this. There is no interruption. Period.
			    e.printStackTrace();
			}
		    }
		} else {
		    outPos = 0;
		}
	    }
	}

	res = outBlock[outPos];
	outPos++;

	if (outPos == outBlock.length) {
	    outBlock = null;
	}

	return res;
    }

    /**
     * Returns and removes the next bit from the head of the Queue.
     * 
     * This method will block if the Queue is empty.
     * 
     * @return 1 if the next bit is 1, 0 if the next bit is 0, and -1 if nothing
     *         is left in the Queue and the input is closed.
     */
    public int takeBit() {
	if (remainingBitsInByte == 0) {
	    currentByte = take();
	    if (currentByte == -1) {
		return -1;
	    }
	    remainingBitsInByte = 7;
	} else {
	    currentByte >>= 1;
	    remainingBitsInByte--;
	}

	return currentByte & 0x01;
    }

    /**
     * Returns and removes {@code numberOfBits} bits from the head of the Queue.
     * 
     * This method blocks if there are not enough bits in this Queue.
     * 
     * @param numberOfBits
     *            The number of bits to return. Valid values are in the range
     *            from 0 to 31. The behaviour for other values is undefined.
     * @return {@code numberOfBits} bits from the head of the Queue or -1 if
     *         there are not enough available and input is closed.
     */
    public int takeBits(int numberOfBits) {
	int res = 0;
	for (int i = 0; i < numberOfBits; i++) {
	    int nextBit = takeBit();
	    if (nextBit == -1) {
		return -1;
	    }
	    res <<= 1;
	    res |= nextBit;
	}
	return res;
    }
}
