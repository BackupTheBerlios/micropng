package micropng.chunk;

import java.util.ArrayList;
import java.util.Iterator;

import micropng.Queue;

public class DataGroup implements Data {

    private ArrayList<Data> dataElements;

    public DataGroup(ArrayList<Data> dataElements) {
	this.dataElements = dataElements;
    }

    @Override
    public byte[] getArray(int from, int length) {
	byte[] res = new byte[length];
	int to = from + length;
	int currentPos = 0;
	int remainingBytes = length;
	Iterator<Data> dataElementsIterator = dataElements.iterator();
	Data currentDataElement = dataElementsIterator.next();
	int nextElementBorder = currentDataElement.getSize();
	int firstPosInFirstChunk;

	while (nextElementBorder < from) {
	    currentPos = nextElementBorder;
	    currentDataElement = dataElementsIterator.next();
	    nextElementBorder = currentDataElement.getSize();
	}

	firstPosInFirstChunk = from - currentPos;

	if (nextElementBorder > to) {
	    return currentDataElement.getArray(firstPosInFirstChunk, length);
	} else {
	    int bytesInFirstChunk = currentDataElement.getSize() - firstPosInFirstChunk;
	    byte[] firstBytes = currentDataElement.getArray(firstPosInFirstChunk, bytesInFirstChunk);
	    System.arraycopy(firstBytes, 0, res, 0, bytesInFirstChunk);
	    remainingBytes -= bytesInFirstChunk;
	    currentDataElement = dataElementsIterator.next();
	}

	while (currentDataElement.getSize() < remainingBytes) {
	    System.arraycopy(currentDataElement.getArray(), 0, res, length - remainingBytes, currentDataElement.getSize());
	    currentDataElement = dataElementsIterator.next();
	}

	System.arraycopy(currentDataElement.getArray(0, remainingBytes), 0, res, length - remainingBytes, remainingBytes);

	return res;
    }

    @Override
    public byte[] getArray() {
	byte[] res = new byte[getSize()];
	int pos = 0;

	for (Data d : dataElements) {
	    System.arraycopy(d.getArray(), 0, res, pos, d.getSize());
	}

	return res;
    }

    @Override
    public int getByteAt(int pos) {

	
	return 0;
    }

    @Override
    public int getSize() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public Queue getStream(int from, int length) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Queue getStream() {
	// TODO Auto-generated method stub
	return null;
    }

}
