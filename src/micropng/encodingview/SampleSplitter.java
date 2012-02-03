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

			for (long i = 0; i < numberOfLines; i++) {
			    int currentByte = in();
			    int remainingBits = 8;
			    for (long j = 0; j < numberOfSamples; j++) {
				if (remainingBits == 0) {
				    currentByte = in();
				    remainingBits = 8;
				}
				remainingBits -= bitsPerSample;
				out((currentByte >> remainingBits) & mask);
			    }
			}
		    } else {
			for (long i = 0; i < numberOfLines; i++) {
			    for (long j = 0; j < numberOfSamples; j++) {
				int sample = in();
				int bitsRead = 8;
				while (bitsRead < bitsPerSample) {
				    sample = (sample << 8) | in();
				    bitsRead += 8;
				}
				out(sample);
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

    public SampleSplitter(CodecInfo codecInfo, Interlace interlacer) {
	this.codecInfo = codecInfo;
	this.dimensions = interlacer.getGraphicsSizes();
    }

    @Override
    public void start() {
	new Thread(new WorkerThread()).start();
    }
}
