package micropng.encodingview;

import micropng.commonlib.StreamFilter;
import micropng.micropng.MicropngThread;

public class SampleSplitter extends StreamFilter {

    private class WorkerThread extends StreamFilter implements MicropngThread {

	private long numberOfSamples;
	private long numberOfLines;

	private WorkerThread(long numberOfSamples, long numberOfLines) {
	    this.numberOfSamples = numberOfSamples;
	    this.numberOfLines = numberOfLines;
	}

	@Override
	public void run() {
	    try {
		if (bitsPerSample < 8) {
		    int mask = (1 << bitsPerSample) - 1;
		    int currentByte;
		    int remainingBits;

		    for (long i = 0; i < numberOfLines; i++) {
			currentByte = in();
			remainingBits = 8;
			for (long j = 0; j < numberOfSamples; j++) {
			    if (remainingBits == 0) {
				currentByte = in();
				remainingBits = 8;
			    }
			    out((currentByte >> (remainingBits - bitsPerSample)) & mask);
			    remainingBits -= bitsPerSample;
			}
		    }
		} else {
		    int sample;
		    int bitsRead;

		    for (long i = 0; i < numberOfLines; i++) {
			for (long j = 0; j < numberOfSamples; j++) {
			    sample = in();
			    bitsRead = 8;
			    while (bitsRead < bitsPerSample) {
				sample <<= 8;
				sample |= in();
				bitsRead += 8;
			    }
			}
		    }
		}
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    private int bitsPerSample;

    public SampleSplitter(int bitsPerSample) {
	this.bitsPerSample = bitsPerSample;
    }

    public void doSplit(long numberOfSamples, long numberOfLines) {
	new WorkerThread(numberOfSamples, numberOfLines);
    }
}
