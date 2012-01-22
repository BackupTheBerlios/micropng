package micropng.commonlib;

import java.util.ArrayList;

public class Queue {

    private static final int blockSize = 0x1 << 10;
    private int[] inBlock;
    private int[] outBlock;
    private boolean closed;
    private Boolean waitingForBufferSwitch;
    private int inPos;
    private int outMax;
    private int outPos;
    private int remainingBitsInByte;
    private int currentByte;

    public Queue() {
	inBlock = new int[blockSize];
	outBlock = new int[blockSize];
	// closed = false;
	waitingForBufferSwitch = new Boolean(false);
	inPos = 0;
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

    private void switchBuffers() {
	if (waitingForBufferSwitch) {
	    int[] tmp = inBlock;
	    inBlock = outBlock;
	    outBlock = tmp;
	    inPos = 0;
	    outPos = 0;
	    waitingForBufferSwitch = false;
	    notify();
	} else {
	    waitingForBufferSwitch = true;
	    notify();
	    try {
		wait();
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
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
	if (inPos == blockSize) {
	    synchronized (this) {
		switchBuffers();
	    }
	}
	inBlock[inPos] = value;
	inPos++;
    }

    /**
     * Close for input: buffers will be internally transferred to output, and
     * put() may not be called any more on this Queue. Any call of put() on this
     * Queue after close() triggers undefined behaviour.
     */
    public void close() {
	synchronized (this) {
	    if (closed) {
		throw new DuplicateCloseException();
	    }

	    if (inPos != 0) {
		if (!waitingForBufferSwitch) {
		    try {
			wait();
		    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}
		outMax = inPos;
		outBlock = inBlock;
		outPos = 0;
		notify();
	    } else {
		if (waitingForBufferSwitch) {
		    //TODO: make this less ugly
		    outMax = 1;
		    outBlock[0] = -1;
		    outPos = 0;
		    notify();
		}
	    }

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
    public int take() {
	int res;

	if (outPos == outMax) {
	    synchronized (this) {
		if (closed) {
		    return -1;
		}
		switchBuffers();
	    }
	}

	res = outBlock[outPos];
	outPos++;

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
