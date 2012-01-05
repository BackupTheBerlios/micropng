package micropng.encodingview;

import micropng.micropng.CodecInfo;
import micropng.micropng.Dimensions;

public class Adam7Interlace extends Interlace {
    private class ReAssemblerThread implements Runnable {

	@Override
	public void run() {
	    Dimensions size = codecInfo.getSize();
	    int width = (int) size.getWidth();
	    int height = (int) size.getHeight();
	    int numberOfChannels = codecInfo.numberOfChannels();

	    int[][][] graphicsMatrix = new int[height][width][numberOfChannels];

	    for (int i = 0; i < numberOfIterations; i++) {
		for (int j = verticalOffsets[i]; j < height; j += verticalStepSizes[i]) {
		    for (int k = horizontalOffsets[i]; k < width; k += horizontalStepSizes[i]) {
			for (int l = 0; l < numberOfChannels; l++) {
			    graphicsMatrix[j][k][l] = in();
			}
		    }
		}
	    }

	    for (int i = 0; i < height; i++) {
		for (int j = 0; j < width; j++) {
		    for (int k = 0; k < numberOfChannels; k++) {
			out(graphicsMatrix[i][j][k]);
		    }
		}
	    }
	}
    }

    private final static int[] horizontalStepSizes = { 8, 8, 4, 4, 2, 2, 1 };
    private final static int[] verticalStepSizes = { 8, 8, 8, 4, 4, 2, 2 };
    private final static int[] horizontalOffsets = { 0, 4, 0, 2, 0, 1, 0 };
    private final static int[] verticalOffsets = { 0, 0, 4, 0, 2, 0, 1 };
    private final static int numberOfIterations = horizontalStepSizes.length;
    private boolean reassemble;
    private CodecInfo codecInfo;

    public Adam7Interlace(boolean reassemble, CodecInfo codecInfo) {
	this.reassemble = reassemble;
	this.codecInfo = codecInfo;
    }
    
    public static Dimensions[] calculate(Dimensions size) {
	Dimensions[] res = new Dimensions[numberOfIterations];
	long width = size.getWidth();
	long height = size.getHeight();
	for (int i = 0; i < horizontalStepSizes.length; i++) {
	    long nextWidth = (width + horizontalOffsets[i] - 1) / horizontalStepSizes[i];
	    long nextHeight = (height + verticalOffsets[i] - 1) / verticalStepSizes[i];
	    res[i] = new Dimensions(nextWidth, nextHeight);
	}
	return res;
    }

    public void start() {
	if (reassemble) {
	    new Thread(new ReAssemblerThread()).start();
	}
    }
}
