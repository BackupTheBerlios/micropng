package micropng.chunk;

public enum SameTypeComparator {
    ALPHABETICAL_ORDERING() {
	@Override
	public int compare(ChunksOrganisationUnit unit0, ChunksOrganisationUnit unit1) {
	    int length0 = unit0.getDataSize();
	    int length1 = unit1.getDataSize();
	    int minLength = Math.min(length0, length1);
	    int pos = 0;

	    while (pos < minLength) {
		int byte0 = unit0.getByteAt(pos);
		int byte1 = unit1.getByteAt(pos);
		if (byte0 != byte1) {
		    return (byte0 < byte1) ? -1 : 1;
		}
		pos++;
	    }
	    return (length0 < length1) ? -1 : 1;
	}
    };

    public abstract int compare(ChunksOrganisationUnit unit0, ChunksOrganisationUnit unit1);
}
