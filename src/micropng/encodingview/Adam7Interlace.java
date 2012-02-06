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
	    done();
	}
    }

    final static int[] horizontalStepSizes = { 8, 8, 4, 4, 2, 2, 1 };
    final static int[] verticalStepSizes = { 8, 8, 8, 4, 4, 2, 2 };
    final static int[] horizontalOffsets = { 0, 4, 0, 2, 0, 1, 0 };
    final static int[] verticalOffsets = { 0, 0, 4, 0, 2, 0, 1 };
    final static int numberOfIterations = horizontalStepSizes.length;
    private final boolean reassemble;
    private final CodecInfo codecInfo;
    private final Dimensions[] graphicsSizes;

    public Adam7Interlace(boolean reassemble, CodecInfo codecInfo) {
	Dimensions size = codecInfo.getSize();
	long width = size.getWidth();
	long height = size.getHeight();

	this.reassemble = reassemble;
	this.codecInfo = codecInfo;
	graphicsSizes = new Dimensions[numberOfIterations];

	for (int i = 0; i < horizontalStepSizes.length; i++) {
	    long nextWidth = (width - horizontalOffsets[i] + horizontalStepSizes[i] - 1)
		    / horizontalStepSizes[i];
	    long nextHeight = (height - verticalOffsets[i] + verticalStepSizes[i] - 1)
		    / verticalStepSizes[i];
	    graphicsSizes[i] = new Dimensions(nextWidth, nextHeight);
	}
    }

    @Override
    public Dimensions[] getGraphicsSizes() {
	return graphicsSizes;
    }

    @Override
    public void start() {
	if (reassemble) {
	    new Thread(new ReAssemblerThread()).start();
	}
    }
}
