package micropng.commonlib;

public class BigArrayOfInt {

    private static final int numberOfLSBs = 30;
    private static final int maximumPrimitiveArraySize = 1 << numberOfLSBs;
    private static final int LSBMask = maximumPrimitiveArraySize - 1;
    public final long size;
    private int[][] data;

    public BigArrayOfInt(long size) {
	int numberOfFullArrays;
	int sizeOfLastArray;
	int totalNumberOfArrays;

	this.size = size;

	numberOfFullArrays = (int) (size >> numberOfLSBs);
	sizeOfLastArray = (int) (size & LSBMask);
	totalNumberOfArrays = (sizeOfLastArray == 0) ? numberOfFullArrays : numberOfFullArrays + 1;
	data = new int[totalNumberOfArrays][];
	for (int i = 0; i < numberOfFullArrays; i++) {
	    data[i] = new int[maximumPrimitiveArraySize];
	}
	if (sizeOfLastArray > 0) {
	    data[numberOfFullArrays] = new int[sizeOfLastArray];
	}
    }

    public int elementAt(long pos) {
	int highAddress = (int) (pos >> numberOfLSBs);
	int lowAddress = (int) (pos & LSBMask);
	return data[highAddress][lowAddress];
    }

    public void set(long pos, int value) {
	int highAddress = (int) (pos >> numberOfLSBs);
	int lowAddress = (int) (pos & LSBMask);
	data[highAddress][lowAddress] = value;
    }
}
