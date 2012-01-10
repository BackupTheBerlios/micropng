package micropng.encodingview;

import micropng.commonlib.StreamFilter;
import micropng.micropng.Dimensions;

public abstract class Interlace extends StreamFilter {
    public abstract void start();

    public abstract Dimensions[] getGraphicsSizes();
}
