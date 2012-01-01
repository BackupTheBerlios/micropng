package micropng.encodingview;

import micropng.commonlib.StreamFilter;
import micropng.micropng.CodecInfo;

public class DeInterlacerMediator extends StreamFilter {

    private StreamFilter endOfQueue;

    public DeInterlacerMediator(StreamFilter input) {
	endOfQueue = input;
    }

    public void deInterlace(CodecInfo properties) {
	int bitsPerSample = properties.getBitDepth();
	Filter filter = null;
	DeInterlacer deInterlacer = null;

	switch (properties.getFilterMethod()) {
	case METHOD_0:
	    filter = new Filter(properties.numberOfChannels(), properties.getBitDepth());
	    endOfQueue.connect(filter);
	    endOfQueue = filter;
	    break;
	}

	if (bitsPerSample != 8) {
	    SampleSplitter splitter = new SampleSplitter(bitsPerSample);
	    endOfQueue.connect(splitter);
	    endOfQueue = splitter;
	}

	switch (properties.getInterlaceMethod()) {
	case NONE:
	    deInterlacer = new NoneDeInterlacer();
	    break;
	case ADAM7:
	    deInterlacer = new Adam7DeInterlacer();
	    break;
	}

	endOfQueue.connect(deInterlacer);

	deInterlacer.deInterlace(properties.getSize(), filter);
    }

}
