package micropng.chunk;

public enum SameTypeComparator {
    ALPHABETICAL_ORDERING() {
	@Override
	public int compare(Chunk chunk0, Chunk chunk1) {
	    int length0 = chunk0.getDataSize();
	    int length1 = chunk1.getDataSize();
	    int minLength = Math.min(length0, length1);
	    int pos = 0;
	    int byte0;
	    int byte1;

	    while (pos < minLength) {
		byte0 = chunk0.getByteAt(pos);
		byte1 = chunk1.getByteAt(pos);
		if (byte0 != byte1) {
		    return (byte0 < byte1) ? -1 : 1;
		}
		pos++;
	    }
	    return (length0 < length1) ? -1 : 1;
	}
    },
    KEEP_INPUT_ORDERING() {
	@Override
	public int compare(Chunk chunk0, Chunk chunk1) {
	    return (chunk0.getId() < chunk1.getId()) ? -1 : 1;
	}
    };
    public abstract int compare(Chunk chunk0, Chunk chunk1);
}
