package micropng.userinterface;

import micropng.chunkview.ChunkSequence;

public class InternalConfiguration {
    private boolean informationalMode;
    private boolean optimizeHuffmanTrees;
    private boolean regroupIDATChunks;
    private boolean removeAncillaryChunks;
    private boolean sortChunks;
    private boolean reencodeZlibStreams;
    private boolean unknownMandatoryChunkInResult;
    private boolean unknownAncillaryChunkInResult;
    private int verbosity;
    private String[] ancillaryChunksToKeep;
    private ChunkSequence chunkSequence;

    public boolean unknownMandatoryChunkInResult() {
	return unknownMandatoryChunkInResult;
    }

    void setUnknownMandatoryChunkInResult(boolean unknownMandatoryChunkInResult) {
	this.unknownMandatoryChunkInResult = unknownMandatoryChunkInResult;
    }

    void setUnknownAncillaryChunkInResult(boolean unknownAncillaryChunkInResult) {
	this.unknownAncillaryChunkInResult = unknownAncillaryChunkInResult;
    }

    public boolean unknownAncillaryChunkInResult() {
	return unknownAncillaryChunkInResult;
    }

    public boolean informationalMode() {
	return informationalMode;
    }

    public boolean optimizeHuffmanTrees() {
	return optimizeHuffmanTrees;
    }

    public boolean regroupIDATChunks() {
	return regroupIDATChunks;
    }

    public boolean removeAncillaryChunks() {
	return removeAncillaryChunks;
    }

    public boolean sortChunks() {
	return sortChunks;
    }

    public boolean reencodeZlibStreams() {
	return reencodeZlibStreams;
    }

    public int getVerbosity() {
	return verbosity;
    }

    void setInformationalMode(boolean informationalMode) {
	this.informationalMode = informationalMode;
    }

    void setOptimizeHuffmanTrees(boolean optimizeHuffmanTrees) {
	this.optimizeHuffmanTrees = optimizeHuffmanTrees;
    }

    void setRegroupIDATChunks(boolean regroupIDATChunks) {
	this.regroupIDATChunks = regroupIDATChunks;
    }

    void setRemoveAncillaryChunks(boolean removeAncillaryChunks) {
	this.removeAncillaryChunks = removeAncillaryChunks;
    }

    void setSortChunks(boolean sortChunks) {
	this.sortChunks = sortChunks;
    }

    void setReencodeZlibStreams(boolean reencodeZlibStreams) {
	this.reencodeZlibStreams = reencodeZlibStreams;
    }

    void setVerbosity(int verbosity) {
	this.verbosity = verbosity;
    }

    void setAncillaryChunksToKeep(String[] ancillaryChunksToKeep) {
	this.ancillaryChunksToKeep = ancillaryChunksToKeep;
    }

    public String[] getAncillaryChunksToKeep() {
	return ancillaryChunksToKeep;
    }

    void setChunkSequence(ChunkSequence chunkSequence) {
	this.chunkSequence = chunkSequence;
    }

    public ChunkSequence getChunkSequence() {
	return chunkSequence;
    }
}
