package micropng.micropng;

import micropng.encodingview.CompressionMethod;
import micropng.encodingview.FilterMethod;
import micropng.encodingview.InterlaceMethod;

public class CodecInfo {
    private Dimensions size;
    private boolean alphaChannel;
    private boolean colour;
    private boolean palette;
    private int bitDepth;
    private CompressionMethod compressionMethod;
    private FilterMethod filterMethod;
    private InterlaceMethod interlaceMethod;

    public void setSize(Dimensions size) {
	this.size = size;
    }

    public boolean hasAlphaChannel() {
	return alphaChannel;
    }

    public void setAlphaChannel(boolean alphaChannel) {
	this.alphaChannel = alphaChannel;
    }

    public boolean isColour() {
	return colour;
    }

    public void setColour(boolean colour) {
	this.colour = colour;
    }

    public boolean isPalette() {
	return palette;
    }

    public void setPalette(boolean palette) {
	this.palette = palette;
    }

    public int getBitDepth() {
	return bitDepth;
    }

    public void setBitDepth(int bitDepth) {
	this.bitDepth = bitDepth;
    }

    public CompressionMethod getCompressionMethod() {
	return compressionMethod;
    }

    public void setCompressionMethod(CompressionMethod compressionMethod) {
	this.compressionMethod = compressionMethod;
    }

    public Dimensions getSize() {
	return size;
    }

    public void setFilterMethod(FilterMethod filterMethod) {
	this.filterMethod = filterMethod;
    }

    public FilterMethod getFilterMethod() {
	return filterMethod;
    }

    public void setInterlaceMethod(InterlaceMethod interlaceMethod) {
	this.interlaceMethod = interlaceMethod;
    }

    public InterlaceMethod getInterlaceMethod() {
	return interlaceMethod;
    }

    public int numberOfChannels() {
	int res = colour ? 3 : 1;
	if (alphaChannel) {
	    res++;
	}
	return res;
    }
}
