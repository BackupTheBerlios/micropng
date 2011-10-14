package micropng.commonlib;

import java.util.ArrayList;

public class BitQueue {

    private Queue input;
    private int remainingBitsInByte;
    private int currentByte;

    public BitQueue(Queue input) {
	this.input = input;
	remainingBitsInByte = 0;
    }

    public int take() throws InterruptedException {
	if (remainingBitsInByte == 0) {
	    currentByte = input.take();
	    remainingBitsInByte = 7;
	} else {
	    currentByte >>= 1;
	    remainingBitsInByte--;
	}

	return currentByte & 0x01;
    }

    public int takeByte() throws InterruptedException {
	return input.take();
    }

    public int take(int numberOfBits) throws InterruptedException {
	int res = 0;
	for (int i = 0; i < numberOfBits; i++) {
	    res <<= 1;
	    res |= take();
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

}
