package micropng.commonlib;

public class RingBuffer extends StreamFilter {
    private int[] outBuffer;
    private int outBufferPointer;

    public RingBuffer(int capacity) {
	this.outBuffer = new int[capacity];
	// outBufferPointer = 0;
    }

    // TODO: think about Arraycopy
    public void repeat(int distance, int length) {
	int startPos = outBufferPointer - distance;
	int lastPos;
	int currentPos;
	int bufferLimit = outBuffer.length;

	if (startPos < 0) {
	    startPos += bufferLimit;
	}

	lastPos = startPos + length;
	currentPos = startPos;

	if (lastPos > bufferLimit) {
	    lastPos -= bufferLimit;
	    while (currentPos < bufferLimit) {
		put(outBuffer[currentPos++]);
	    }
	    currentPos = 0;
	}

	while (currentPos < lastPos) {
	    put(outBuffer[currentPos++]);
	}
    }

    public void put(int value) {
	outBuffer[outBufferPointer] = value;
	outBufferPointer = (outBufferPointer + 1) % outBuffer.length;
	out(value);
    }
}
