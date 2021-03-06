package micropng.chunkview.chunk;

import java.util.ArrayList;
import java.util.Iterator;

import micropng.commonlib.Queue;

public class DataGroup implements DataField {

    private class QueueFeeder implements Runnable {

	private final Queue out;

	public QueueFeeder(Queue out) {
	    this.out = out;
	}

	@Override
	public void run() {
	    for (DataField d : dataElements) {
		Queue q = d.getStream();
		int value;
		value = q.take();
		while (value != -1) {
		    out.put(value);
		    value = q.take();
		}
	    }
	    out.close();
	}
    }

    private final ArrayList<DataField> dataElements;

    public DataGroup(ArrayList<DataField> dataElements) {
	this.dataElements = dataElements;
    }

    @Override
    public byte[] getArray(int from, int length) {
	final byte[] res;
	final int to = from + length;
	int currentPos = 0;
	int remainingBytes = length;
	Iterator<DataField> dataElementsIterator = dataElements.iterator();
	DataField currentDataElement = dataElementsIterator.next();
	int nextElementBorder = currentDataElement.getSize();
	int firstPosInFirstChunk;
	int lengthInFirstChunk;
	byte[] firstBytes;

	while (nextElementBorder <= from) {
	    currentDataElement = dataElementsIterator.next();
	    currentPos = nextElementBorder;
	    nextElementBorder += currentDataElement.getSize();
	}

	firstPosInFirstChunk = from - currentPos;

	if (nextElementBorder >= to) {
	    return currentDataElement.getArray(firstPosInFirstChunk, length);
	}

	res = new byte[length];
	lengthInFirstChunk = currentDataElement.getSize() - firstPosInFirstChunk;
	firstBytes = currentDataElement.getArray(firstPosInFirstChunk, lengthInFirstChunk);
	System.arraycopy(firstBytes, 0, res, 0, lengthInFirstChunk);
	remainingBytes -= lengthInFirstChunk;
	currentDataElement = dataElementsIterator.next();

	while (currentDataElement.getSize() < remainingBytes) {
	    System.arraycopy(currentDataElement.getArray(), 0, res, length - remainingBytes,
		    currentDataElement.getSize());
	    currentDataElement = dataElementsIterator.next();
	}

	System.arraycopy(currentDataElement.getArray(0, remainingBytes), 0, res, length
		- remainingBytes, remainingBytes);

	return res;
    }

    @Override
    public byte[] getArray() {
	byte[] res = new byte[getSize()];
	int pos = 0;

	for (DataField d : dataElements) {
	    System.arraycopy(d.getArray(), 0, res, pos, d.getSize());
	    pos += d.getSize();
	}

	return res;
    }

    @Override
    public int getByteAt(int pos) {
	int currentPos = 0;
	Iterator<DataField> dataElementsIterator = dataElements.iterator();
	DataField currentDataElement = dataElementsIterator.next();

	while (currentPos + currentDataElement.getSize() <= pos) {
	    currentPos += currentDataElement.getSize();
	}

	return currentDataElement.getByteAt(pos - currentPos);
    }

    @Override
    public int getSize() {
	int res = 0;

	for (DataField d : dataElements) {
	    res += d.getSize();
	}
	return res;
    }

    /*
     * @Override public Queue getStream(int from, int length) { // TODO
     * Auto-generated method stub return null; }
     */
    @Override
    public Queue getStream() {
	Queue res = new Queue();
	new Thread(new QueueFeeder(res)).start();
	return res;
    }
}
