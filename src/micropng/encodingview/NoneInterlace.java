package micropng.encodingview;

import micropng.micropng.Dimensions;

public class NoneInterlace extends Interlace {
    private final Dimensions[] graphicsSizes;

    public NoneInterlace(Dimensions size) {
	this.graphicsSizes = new Dimensions[] { size };
    }

    @Override
    public Dimensions[] getGraphicsSizes() {
	return graphicsSizes;
    }
}
