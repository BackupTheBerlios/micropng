package micropng.chunk;

import java.util.ArrayList;

import micropng.Queue;

public class DataGroup implements Data {

    private ArrayList<Data> dataElements;

    public DataGroup(ArrayList<Data> dataElements) {
	this.dataElements = dataElements;
    }

    @Override
    public byte[] getArray(int from, int length) {
	byte[] res = new byte[length];
	int bytesRead = 0;
	Data nextDataElement = dataElements.get(0);

	while (bytesRead + nextDataElement.getSize() < length) {
	    System.arraycopy(nextDataElement.getArray(), 0, res, bytesRead, nextDataElement.getSize());
	    bytesRead += nextDataElement.getSize();
	}

	return res;
    }

    @Override
    public byte[] getArray() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public int getByteAt(int pos) {
	// TODO Auto-generated method stub
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
