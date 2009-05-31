package micropng.chunk;

public interface Chunk {

    public boolean isAncillary();
    public boolean isPrivate();
    public boolean isThirdByteUpperCase();
    public boolean isSafeToCopy();
}
