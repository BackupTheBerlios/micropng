package micropng.commonlib;

public abstract class StreamFilter {
    private Queue input;
    private Queue output;

    public void connect(StreamFilter nextInChain) {
	if (nextInChain.input == null) {
	    nextInChain.input = new Queue();
	}
	output = nextInChain.input;
    }

    public final int in() {
	return input.take();
    }

    public final void out(int value) {
	output.put(value);
    }

    public final void done() {
	output.close();
    }

    public Queue getInputQueue() {
	return input;
    }

    public void shareCurrentInputChannel(StreamFilter target) {
	target.input = input;
    }

    public void shareCurrentOutputChannel(StreamFilter target) {
	target.output = output;
    }
}
