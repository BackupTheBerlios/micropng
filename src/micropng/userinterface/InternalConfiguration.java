package micropng.userinterface;

import java.io.File;

import micropng.chunkview.ChunkSequence;

public class InternalConfiguration {
    // private String[] ancillaryChunksToKeep;
    private ChunkSequence chunkSequence;
    // private boolean informationalMode;
    // private boolean optimizeHuffmanTrees;
    private File outputFile;
    // private boolean reencodeZlibStreams;
    private boolean regroupIDATChunks;
    // private boolean removeAncillaryChunks;
    private boolean removeUselessSBIT;
    private boolean sortChunks;
    private boolean unknownMandatoryChunk;
    private boolean unknownAncillaryChunk;
    // private int verbosity;

    public ChunkSequence getChunkSequence() {
	return chunkSequence;
    }

    void setChunkSequence(ChunkSequence chunkSequence) {
	this.chunkSequence = chunkSequence;
    }

    public File getOutputFile() {
        return outputFile;
    }

    void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public boolean regroupIDATChunks() {
	return regroupIDATChunks;
    }

    void setRegroupIDATChunks(boolean regroupIDATChunks) {
	this.regroupIDATChunks = regroupIDATChunks;
    }

    public boolean removeUselessSBIT() {
	return removeUselessSBIT;
    }

    void setRemoveUselessSBIT(boolean removeUselessSBIT) {
	this.removeUselessSBIT = removeUselessSBIT;
    }
    
    public boolean sortChunks() {
	return sortChunks;
    }

    void setSortChunks(boolean sortChunks) {
	this.sortChunks = sortChunks;
    }

    public boolean unknownAncillaryChunk() {
	return unknownAncillaryChunk;
    }

    void setUnknownAncillaryChunk(boolean unknownAncillaryChunk) {
	this.unknownAncillaryChunk = unknownAncillaryChunk;
    }

    public boolean unknownMandatoryChunk() {
	return unknownMandatoryChunk;
    }

    void setUnknownMandatoryChunk(boolean unknownMandatoryChunk) {
	this.unknownMandatoryChunk = unknownMandatoryChunk;
    }

    // public boolean informationalMode() {
    // return informationalMode;
    // }
    //
    // public boolean optimizeHuffmanTrees() {
    // return optimizeHuffmanTrees;
    // }
    //
    // public boolean removeAncillaryChunks() {
    // return removeAncillaryChunks;
    // }
    //
    // public boolean reencodeZlibStreams() {
    // return reencodeZlibStreams;
    // }
    //
    // public int getVerbosity() {
    // return verbosity;
    // }
    //
    // void setInformationalMode(boolean informationalMode) {
    // this.informationalMode = informationalMode;
    // }
    //
    // void setOptimizeHuffmanTrees(boolean optimizeHuffmanTrees) {
    // this.optimizeHuffmanTrees = optimizeHuffmanTrees;
    // }

    // void setRemoveAncillaryChunks(boolean removeAncillaryChunks) {
    // this.removeAncillaryChunks = removeAncillaryChunks;
    // }

    // void setReencodeZlibStreams(boolean reencodeZlibStreams) {
    // this.reencodeZlibStreams = reencodeZlibStreams;
    // }
    //
    // void setVerbosity(int verbosity) {
    // this.verbosity = verbosity;
    // }
    //
    // void setAncillaryChunksToKeep(String[] ancillaryChunksToKeep) {
    // this.ancillaryChunksToKeep = ancillaryChunksToKeep;
    // }
    //
    // public String[] getAncillaryChunksToKeep() {
    // return ancillaryChunksToKeep;
    // }
}
