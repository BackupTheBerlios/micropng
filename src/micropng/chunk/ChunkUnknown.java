package micropng.chunk;

public class ChunkUnknown implements Chunk {

    private int[] type;
    private DataField data;
    private int[] crc;

    @Override
    public boolean isAncillary() {
	return (type[0] & 0x10) != 0;
    }

    @Override
    public boolean isPrivate() {
	return (type[1] & 0x10) != 0;
    }

    @Override
    public boolean isSafeToCopy() {
	return (type[3] & 0x10) != 0;
    }

    @Override
    public boolean isThirdByteUpperCase() {
	return (type[2] & 0x10) != 0;
    }
}
