package micropng.commonlib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Queue {

    private ConcurrentLinkedQueue<int[]> queue;
    private int[] inBlock;
    private int[] outBlock;
    private static final int blockSize = 0x1 << 10;
    private boolean closed;

    private int inPos;
    private int outPos;
    private int remainingBitsInByte;
    private int currentByte;

    public Queue() {
	queue = new ConcurrentLinkedQueue<int[]>();
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
	    remainingBitsInByte--;
	    res.add(currentByte & 0x01);
	    currentByte >>= 1;
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
	int res;
	if (remainingBitsInByte == 0) {
	    currentByte = take();
	    if (currentByte == -1) {
		return -1;
	    }
	    remainingBitsInByte = 7;
	} else {
	    remainingBitsInByte--;
	}

	res = currentByte & 0x01;
	currentByte >>= 1;

	return res;
    }

    /**
     * Returns and removes {@code numberOfBits} bits from the head of the Queue.
     * 
     * The bits are returned in the same order they are located in the input
     * byte(s), crossing byte boundaries from LSB upwards. This method blocks if
     * there are not enough bits in this Queue.
     * 
     * @param numberOfBits
     *            The number of bits to return. Valid values are in the range
     *            from 0 to 31. The behaviour for other values is undefined.
     * @return {@code numberOfBits} bits from the head of the Queue.
     */
    public int takeBits(int numberOfBits) {
	int res = 0;
	int bitsLeftToTake = numberOfBits;
	int bitsTaken = 0;

	while (bitsLeftToTake > 0) {
	    if (bitsLeftToTake > remainingBitsInByte) {
		int nextBitMask = currentByte << bitsTaken;
		res |= nextBitMask;
		bitsTaken += remainingBitsInByte;
		bitsLeftToTake -= remainingBitsInByte;

		currentByte = take();
		if (currentByte == -1) {
		    throw new InputClosedEarlyException();
		}
		remainingBitsInByte = 8;
	    } else {
		int lowPartMask = (1 << bitsLeftToTake) - 1;
		int nextBitMask = (currentByte & lowPartMask) << bitsTaken;
		res |= nextBitMask;
		currentByte >>= bitsLeftToTake;
		remainingBitsInByte -= bitsLeftToTake;
		bitsLeftToTake = 0;
	    }
	}

	return res;
    }
}
