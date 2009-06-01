package micropng.chunk;

public class Chunk {

    private Type type;

    public boolean isAncillary() {
	return type.isAncillary();
    }
    public boolean isPrivate() {
	return type.isPrivate();
    }
    public boolean isThirdByteUpperCase() {
	return type.isThirdByteUpperCase();
    }
    public boolean isSafeToCopy() {
	return type.isSafeToCopy();
    }
    
}
