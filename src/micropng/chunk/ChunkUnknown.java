package micropng.chunk;

public class ChunkUnknown implements Chunk {

    private int[] type;
    private DataField data;
    private int[] crc;

    @Override
    public boolean isAncillary() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isPrivate() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isSafeToCopy() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isThirdByteUpperCase() {
	// TODO Auto-generated method stub
	return false;
    }

}
