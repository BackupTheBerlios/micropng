package micropng.micropng;

import java.util.TreeSet;

public class Configurator {

    private TreeSet<Integer> knownChunks;

    public Configurator(Configuration userConfiguration) {
	knownChunks = new TreeSet<Integer>();
    }

    private int convertStringToInt(String s) {
	int integerRepresentation = 0;
	for (int i = 0; i < 4; i++) {
	    integerRepresentation <<= 8;
	    integerRepresentation += s.charAt(i);
	}
	return integerRepresentation;
    }

    public void addKnownChunkType(String chunkType) {
	addKnownChunkType(convertStringToInt(chunkType));
    }

    public void addKnownChunkType(int chunkType) {
	knownChunks.add(chunkType);
    }

    public boolean isKnownChunkType(String chunkType) {
	return isKnownChunkType(convertStringToInt(chunkType));
    }

    public boolean isKnownChunkType(int chunkType) {
	return knownChunks.contains(chunkType);
    }
}
