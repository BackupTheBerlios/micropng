package micropng.encodingview;

import micropng.commonlib.StreamFilter;
import micropng.micropng.CodecInfo;

public class DeInterlacerMediator extends StreamFilter {
    public void deInterlace(CodecInfo codecInfo) {
	int bitsPerSample = codecInfo.getBitDepth();
	Filter filter = null;
	DeInterlacerFilterController deInterlacerFilterController = null;
	Interlace deInterlacer = null;

	switch (codecInfo.getFilterMethod()) {
	case METHOD_0:
	    filter = new Filter(codecInfo.numberOfChannels(), codecInfo.getBitDepth());
	    shareCurrentInputChannel(filter);
	    break;
	}

	switch (codecInfo.getInterlaceMethod()) {
	case NONE:
	    deInterlacerFilterController = new NoneInterlacerDecoderFilterController(codecInfo
		    .getSize(), filter);
	    deInterlacer = new NoneInterlace();
	    break;
	case ADAM7:
	    deInterlacerFilterController = new Adam7DecoderFilterController(codecInfo.getSize(),
		    filter);
	    deInterlacer = new Adam7Interlace(true, codecInfo);
	    break;
	}

	if (bitsPerSample != 8) {
	    SampleSplitter splitter = new SampleSplitter(bitsPerSample);
	    filter.connect(splitter);
	    splitter.connect(deInterlacer);
	} else {
	    filter.connect(deInterlacer);
	}

	deInterlacerFilterController.start();
	shareCurrentOutputChannel(deInterlacer);
	deInterlacer.start();
    }
}
