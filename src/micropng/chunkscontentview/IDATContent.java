package micropng.chunkscontentview;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.commonlib.Queue;
import micropng.commonlib.StreamFilter;

public class IDATContent extends StreamFilter {
    private class QueueFeeder implements Runnable {
	// TODO: make this faster
	@Override
	public void run() {
	    for (Chunk c : chunkSequence) {
		Queue in = c.getData().getStream();

		int next = in.take();
		while (next != -1) {
		    out(next);
		    next = in.take();
		}
	    }
	    done();
	}
    }

    final ChunkSequence chunkSequence;

    public IDATContent(ChunkSequence chunkSequence) {
	this.chunkSequence = chunkSequence;
    }

    public void start() {
	new Thread(new QueueFeeder()).start();
    }
}
