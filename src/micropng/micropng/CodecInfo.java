package micropng.micropng;

import micropng.encodingview.CompressionMethod;
import micropng.encodingview.FilterMethod;
import micropng.encodingview.InterlaceMethod;
import micropng.fileview.ColourType;

public class CodecInfo {
    private Dimensions size;
    private ColourType colourType;
    private int bitDepth;
    private CompressionMethod compressionMethod;
    private FilterMethod filterMethod;
    private InterlaceMethod interlaceMethod;

    public void setSize(Dimensions size) {
	this.size = size;
    }

    public void setColourType(int colourType) {
	this.colourType = ColourType.byInt(colourType);
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

    public boolean isPalette() {
	return colourType.isPalette();
    }

    public boolean isColour() {
	return colourType.isColour();
    }

    public boolean hasAlphaChannel() {
	return colourType.hasAlphaChannel();
    }

    public int numberOfChannels() {
	return colourType.numberOfChannels();
    }
}
