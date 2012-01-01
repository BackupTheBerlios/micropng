package micropng.commonlib;

public class RingBuffer extends StreamFilter {

    private int[] outBuffer;
    private int outBufferPointer;

    public RingBuffer(int capacity) {
	this.outBuffer = new int[capacity];
    }

    // TODO: this can be much faster
    public void repeat(int distance, int length) {
	int sourcePointer = (outBufferPointer - length) % outBuffer.length;
	for (int i = 0; i < length; i++) {
	    out(outBuffer[sourcePointer]);
	    sourcePointer = (sourcePointer + 1) % outBuffer.length;
	}
    }

    public void out(int value) {
	outBuffer[outBufferPointer] = value;
	outBufferPointer = (outBufferPointer + 1) % outBuffer.length;
	super.out(value);
    }
}
