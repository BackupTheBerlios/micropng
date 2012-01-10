package micropng.encodingview;

import micropng.commonlib.StreamFilter;
import micropng.micropng.CodecInfo;
import micropng.micropng.Dimensions;

public class SampleSplitter extends StreamFilter {

    private class WorkerThread implements Runnable {

	@Override
	public void run() {
	    int bitsPerSample = codecInfo.getBitDepth();
	    int numberOfChannels = codecInfo.numberOfChannels();

	    for (Dimensions dimension : dimensions) {
		if (!dimension.isEmpty()) {
		    long numberOfLines = dimension.getHeight();
		    long numberOfSamples = numberOfChannels * dimension.getWidth();

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
		    } else if (bitsPerSample > 8) {
			for (long i = 0; i < numberOfLines; i++) {
			    for (long j = 0; j < numberOfSamples; j++) {
				int sample = in();
				int bitsRead = 8;
				while (bitsRead < bitsPerSample) {
				    sample <<= 8;
				    sample |= in();
				    bitsRead += 8;
				}
				out(sample);
			    }
			}
		    } else {
			for (long i = 0; i < numberOfLines; i++) {
			    for (long j = 0; j < numberOfSamples; j++) {
				out(in());
			    }
			}
		    }
		}
	    }
	    done();
	}
    }

    private CodecInfo codecInfo;
    private Dimensions[] dimensions;

    public SampleSplitter(CodecInfo codecInfo) {
	this.codecInfo = codecInfo;
    }

    public void start(Dimensions[] dimensions) {
	this.dimensions = dimensions;
	new Thread(new WorkerThread()).start();
    }
}
