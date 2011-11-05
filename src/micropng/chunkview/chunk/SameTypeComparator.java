package micropng.chunkview.chunk;

public enum SameTypeComparator {
    ALPHABETICAL_ORDERING() {
	@Override
	public int compare(OrganisationUnit unit0, OrganisationUnit unit1) {
	    long length0 = unit0.getDataSize();
	    long length1 = unit1.getDataSize();
	    long minLength = Math.min(length0, length1);
	    long pos = 0;

	    while (pos < minLength) {
		int byte0 = unit0.getByteAt(pos);
		int byte1 = unit1.getByteAt(pos);
		if (byte0 != byte1) {
		    return (byte0 < byte1) ? -1 : 1;
		}
		pos++;
	    }
	    if (length0 < length1) {
		return -1;
	    }
	    if (length0 > length1) {
		return 1;
	    }
	    return 0;
	}
    };

    public abstract int compare(OrganisationUnit unit0, OrganisationUnit unit1);
}
