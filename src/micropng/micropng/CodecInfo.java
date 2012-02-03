package micropng.micropng;

import java.nio.ByteBuffer;

import micropng.chunkview.chunk.Chunk;
import micropng.encodingview.CompressionMethod;
import micropng.encodingview.FilterMethod;
import micropng.encodingview.InterlaceMethod;
import micropng.fileview.ColourType;

//TODO: convert this to micropng.chunkscontentview.Header
public class CodecInfo {
    private Dimensions size;
    private int bitDepth;
    private ColourType colourType;
    private CompressionMethod compressionMethod;
    private FilterMethod filterMethod;
    private InterlaceMethod interlaceMethod;

    public CodecInfo(Chunk headerChunk) {
	ByteBuffer byteBuffer = ByteBuffer.wrap(headerChunk.getData().getArray());
	int width = byteBuffer.getInt();
	int height = byteBuffer.getInt();

	size = new Dimensions(width, height);
	bitDepth = byteBuffer.get();
	colourType = ColourType.byInt(byteBuffer.get());
	compressionMethod = CompressionMethod.getMethod(byteBuffer.get());
	filterMethod = FilterMethod.getMethod(byteBuffer.get());
	interlaceMethod = InterlaceMethod.getMethod(byteBuffer.get());
    }

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

    public int getSampleDepth() {
	if (colourType == ColourType.INDEXED_COLOUR) {
	    return 8;
	}
	return bitDepth;
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

    /**
     * Return the number of channels in the IDAT chunks.
     * 
     * The exact number of channels that are actually part of the zlib data
     * stream. Any other channels of this file that come from other chunks like
     * PLTE or tRNS are not included.
     * 
     * @return the number of channels in the IDAT chunks
     */
    public int numberOfChannels() {
	return colourType.numberOfChannels();
    }

    public ColourType getColourType() {
	return colourType;
    }
}
