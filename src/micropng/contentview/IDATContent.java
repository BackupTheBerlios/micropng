package micropng.contentview;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.Data;
import micropng.commonlib.Queue;
import micropng.micropng.MicropngThread;

public class IDATContent implements Data {
    private class QueueFeeder extends MicropngThread {

	private Queue out;

	public QueueFeeder(Queue out) {
	    this.out = out;
	}

	// TODO: make this faster
	@Override
	public void run() {
	    try {
		for (Chunk c : chunkSequence) {
		    Queue in = c.getData().getStream();

		    int next = in.take();
		    while (next != -1) {
			out.put(next);
			next = in.take();
		    }
		}
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }

    private ChunkSequence chunkSequence;

    public IDATContent(ChunkSequence chunkSequence) {
	this.chunkSequence = chunkSequence;
    }

    public Queue getStream() {
	Queue res = new Queue();
	new Thread(new QueueFeeder(res)).run();
	return res;
    }

}
