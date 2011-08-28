package micropng.micropng;

import java.io.IOException;

import micropng.chunkview.ChunkSequence;

public class UserConfiguration {
    private String path;
    private boolean infoMode;
    private boolean verboseMode;
    private boolean aggregateIDAT;
    private boolean optimizeHuffmanTrees;
    private boolean removeAncillaryChunks;
    private int[] ancillaryChunksToKeep;
    private int[] ancillaryChunksToRemove;
    private ChunkSequence chunkSequence;

    public UserConfiguration generateInitiatedConfiguration() throws IOException {
	Configurator configurator = new Configurator();
	UserConfiguration res = null;
	return res;
    }

    public ChunkSequence getChunkSequence() {
	return chunkSequence;
    }

    public void setPath(String path) {
	this.path = path;
    }

    public String getPath() {
	return new String(path);
    }

    public boolean isInfoMode() {
	return infoMode;
    }

    public void setInfoMode(boolean infoMode) {
	this.infoMode = infoMode;
    }

    public boolean isVerboseMode() {
	return verboseMode;
    }

    public void setVerboseMode(boolean verboseMode) {
	this.verboseMode = verboseMode;
    }

    public boolean doesRemoveAncillaryChunks() {
	return removeAncillaryChunks;
    }

    public void setRemoveAncillaryChunks(boolean removeAncillaryChunks) {
	this.removeAncillaryChunks = removeAncillaryChunks;
    }

    public void setAggregateIDAT(boolean aggregateIDAT) {
	this.aggregateIDAT = aggregateIDAT;
    }

    public boolean doesAggregateIDAT() {
	return aggregateIDAT;
    }

    public void setOptimizeHuffmanTrees(boolean optimizeHuffmanTrees) {
	this.optimizeHuffmanTrees = optimizeHuffmanTrees;
    }

    public boolean doesOptimizeHuffmanTrees() {
	return optimizeHuffmanTrees;
    }
}
