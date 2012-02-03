package micropng.encodingview;

import micropng.commonlib.StreamFilter;
import micropng.micropng.Dimensions;

public abstract class Interlace extends StreamFilter {
    public abstract Dimensions[] getGraphicsSizes();
}
