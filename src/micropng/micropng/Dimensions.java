package micropng.micropng;

public class Dimensions {
    private long width;
    private long height;

    public Dimensions(long width, long height) {
	this.width = width;
	this.height = height;
    }

    public long getWidth() {
	return width;
    }

    public long getHeight() {
	return height;
    }

    public boolean isEmpty() {
	return (width == 0) || (height == 0);
    }
}
