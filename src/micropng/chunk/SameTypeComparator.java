package micropng.chunk;

public enum SameTypeComparator {
    ALPHABETICAL_ORDERING () {
	@Override
	public int compare(Chunk chunk0, Chunk chunk1) {
	    //TODO: implement
	    return -2;
	}
    },
    KEEP_INPUT_ORDERING () {
	@Override
	public int compare(Chunk chunk0, Chunk chunk1) {
	    //TODO: implement
	    return -2;
	}
    };
    public abstract int compare(Chunk chunk0, Chunk chunk1);
}
