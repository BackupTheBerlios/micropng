package micropng.commonlib;

public class RingBuffer {

    private int[] outBuffer;
    private int outBufferPointer;
    private Queue output;

    public RingBuffer(Queue output, int capacity) {
	this.output = output;
	this.outBuffer = new int[capacity];
    }

    // TODO: this can be much faster
    public void repeat(int distance, int length) throws InterruptedException {
	int sourcePointer = (outBufferPointer - length) % outBuffer.length;
	for (int i = 0; i < length; i++) {
	    put(outBuffer[sourcePointer]);
	    sourcePointer = (sourcePointer + 1) % outBuffer.length;
	}
    }

    public void put(int value) throws InterruptedException {
	outBuffer[outBufferPointer] = value;
	outBufferPointer = (outBufferPointer + 1) % outBuffer.length;
	output.put(value);
    }
}
