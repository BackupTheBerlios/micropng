package micropng.zlib.deflate;

import java.util.ArrayList;

import micropng.commonlib.StreamFilter;

public abstract class DataBlockHeader extends StreamFilter {
    public abstract void decode();

    public abstract ArrayList<Integer> getOriginalHeader();
}
