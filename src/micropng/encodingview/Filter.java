package micropng.encodingview;

import micropng.commonlib.BigArrayOfInt;

public class Filter {

    private int numberOfChannels;
    private BigArrayOfInt lastScanlineRed;
    private BigArrayOfInt lastScanlineGreen;
    private BigArrayOfInt lastScanlineBlue;
    private BigArrayOfInt lastScanlineAlpha;
    private BigArrayOfInt lastScanlineGrey;

    public Filter(int numberOfChannels, int bitsPerSample, long imageWidth) {
	
	//lastScanline = new int[][(int) imageWidth][numberOfChannels];
    }
}
