package micropng.commonlib;

import java.util.ArrayList;

public class BitArrayListConverter {
    public static void append(int source, ArrayList<Integer> target, int numberOfBits) {
	int shiftingVariable = source;
	int reverseSource = 0;

	for (int i = 0; i < numberOfBits; i++) {
	    reverseSource <<= 1;
	    reverseSource |= (shiftingVariable & 0x01);
	    shiftingVariable >>= 1;
	}

	shiftingVariable = reverseSource;

	for (int i = 0; i < numberOfBits; i++) {
	    int newElement = (shiftingVariable & 0x01);
	    target.add(newElement);
	    shiftingVariable >>= 1;
	}
    }
}
