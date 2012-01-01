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

    public int in() throws InterruptedException {
	return input.take();
    }

    public void out(int value) throws InterruptedException {
	output.put(value);
    }

    public void done() throws InterruptedException {
	output.close();
    }

    public Queue getInputQueue() {
	return input;
    }
}
