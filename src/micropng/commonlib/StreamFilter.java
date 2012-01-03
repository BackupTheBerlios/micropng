package micropng.commonlib;

public abstract class StreamFilter {
    private Queue input;
    private Queue output;

    public StreamFilter() {
	this.input = null;
	this.output = null;
    }

    public void connect(StreamFilter nextInChain) {
	if (nextInChain.input == null) {
	    nextInChain.input = new Queue();
	}
	output = nextInChain.input;
    }

    public int in() {
	return input.take();
    }

    public void out(int value) {
	output.put(value);
    }

    public void done() {
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

//    public void shortCut() {
//	input.shortCut(output);
//    }
}
