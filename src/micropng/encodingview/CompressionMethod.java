package micropng.encodingview;

public enum CompressionMethod {

    METHOD_0;

    public static CompressionMethod getMethod(int i) {
	return CompressionMethod.values()[i];
    }
}
