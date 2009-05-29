package micropng;

import java.util.concurrent.ArrayBlockingQueue;

public class MicroPNGQueue {

	private ArrayBlockingQueue<int[]> queue;
	private int[] inBlock;
	private int[] outBlock;
	private int blockSize;

	private int inPos;
	private int outPos;

	public MicroPNGQueue() {
		blockSize = 0x1 << 10;
		inPos = 0;
		outPos = 0;
		queue = new ArrayBlockingQueue<int[]>(2);
	}

	public int take() throws InterruptedException {
		int res;

		if (outBlock == null) {
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
			queue.put(inBlock);
			inBlock = null;
		}
	}

	public void flush() throws InterruptedException {

		if (inBlock != null) {
			queue.put(inBlock);
		}
		inBlock = null;
	}
}
