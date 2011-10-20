package micropng.micropng;

public class EncodingProperties {
    private boolean alphaChannel;
    private boolean colour;
    private boolean palette;
    private boolean interlaced;
    private int bitDepth;

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

    public boolean isInterlaced() {
	return interlaced;
    }

    public void setInterlaced(boolean interlaced) {
	this.interlaced = interlaced;
    }

    public int getBitDepth() {
	return bitDepth;
    }

    public void setBitDepth(int bitDepth) {
	this.bitDepth = bitDepth;
    }

}
