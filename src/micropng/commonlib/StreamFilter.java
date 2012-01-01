package micropng.commonlib;

public abstract class StreamFilter {
    private Queue input;
    private Queue output;

    public StreamFilter() {
	this.input = null;
	this.output = null;
    }

    public void connect(StreamFilter nextInChain) {
	Queue connection = new Queue();
	output = connection;
	nextInChain.input = connection;
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

    public void shortCut() {
	input.shortCut(output);
    }
}
