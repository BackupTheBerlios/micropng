package micropng.encodingview;

public interface DeInterlacer {

    public void deInterlace(long width, long height, Filter filter) throws InterruptedException;
}
