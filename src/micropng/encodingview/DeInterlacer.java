package micropng.encodingview;

import micropng.commonlib.StreamFilter;
import micropng.micropng.Dimensions;

public abstract class DeInterlacer extends StreamFilter {

    public abstract void deInterlace(Dimensions size, Filter filter);
}
