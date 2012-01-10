package micropng.zlib.deflate;

import java.util.ArrayList;

import micropng.commonlib.RingBuffer;
import micropng.commonlib.StreamFilter;

public abstract class DataBlockHeader extends StreamFilter {
    public abstract ArrayList<Integer> getOriginalHeader();

    public abstract void decode(RingBuffer output);
}
