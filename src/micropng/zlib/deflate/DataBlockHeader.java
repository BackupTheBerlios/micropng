package micropng.zlib.deflate;

import java.util.ArrayList;

import micropng.commonlib.Queue;

public interface DataBlockHeader {
    public void decode(Queue output) throws InterruptedException;
    public ArrayList<Integer> getOriginalHeader();
}
