package micropng.commonlib;

import java.util.ArrayList;

public class Queue {

    private static final int blockSize = 2048;
    private int[] inBlock;
    private int[] outBlock;
    private boolean closed;
    private boolean outWaitingForBufferSwitch;
    private int inPos;
    private int outMax;
    private int outPos;
    private int remainingBitsInByte;
    private int currentByte;

    public Queue() {
	inBlock = new int[blockSize];
	outBlock = new int[blockSize];
	// closed = false;
	outWaitingForBufferSwitch = true;
	// inPos = 0;
	outMax = blockSize;
	outPos = outMax;
	// remainingBitsInByte = 0;
	// currentByte = <uninitialized>;
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
     * If this {@code Queue} is full, block until the new value can be stored.
     * 
     * @param value
     *            The byte to append on this Queue, stored in the LSB of the
     *            primitive int.
     */
    public final void put(int value) {
	if (inPos == blockSize) {
	    int[] tmp = inBlock;
	    inBlock = outBlock;
	    inPos = 0;

	    synchronized (this) {
		while (!outWaitingForBufferSwitch) {
		    try {
			wait();
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		}
		outBlock = tmp;
		outPos = 0;
		outWaitingForBufferSwitch = false;
		notify();
	    }
	}

	inBlock[inPos++] = value;
    }

    /**
     * Close for input: buffers will be internally transferred to output, and
     * put() may not be called any more on this Queue. Any call of put() on this
     * Queue after close() triggers undefined behaviour. Depending on the
     * internal state, this method may block until more values are removed with
     * take().
     */
    public final void close() {
	synchronized (this) {
	    if (closed) {
		throw new DuplicateCloseException();
	    }

	    while (!outWaitingForBufferSwitch) {
		try {
		    wait();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	    outMax = inPos;
	    outBlock = inBlock;
	    outPos = 0;
	    outWaitingForBufferSwitch = false;
	    notify();

	    closed = true;
	}
    }

    /**
     * Returns and removes the next value waiting in this queue.
     * 
     * If there is no value waiting, block until a new value is available or the
     * stream is closed.
     * 
     * @return the next value in stream or -1 if nothing is left
     */
    public final int take() {
	while (outPos == outMax) {
	    synchronized (this) {
		if (closed) {
		    return -1;
		}
		outWaitingForBufferSwitch = true;
		notify();
		while (outWaitingForBufferSwitch) {
		    try {
			wait();
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		}
	    }
	}

	return outBlock[outPos++];
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

	while (bitsLeftToTake > remainingBitsInByte) {
	    int nextBitMask = currentByte << bitsTaken;
	    res |= nextBitMask;
	    bitsTaken += remainingBitsInByte;
	    bitsLeftToTake -= remainingBitsInByte;

	    currentByte = take();
	    if (currentByte == -1) {
		throw new InputClosedEarlyException();
	    }
	    remainingBitsInByte = 8;
	}

	if (bitsLeftToTake > 0) {
	    int lowPartMask = (1 << bitsLeftToTake) - 1;
	    int nextBitMask = (currentByte & lowPartMask) << bitsTaken;
	    res |= nextBitMask;
	    currentByte >>= bitsLeftToTake;
	    remainingBitsInByte -= bitsLeftToTake;
	}

	return res;
    }
}
