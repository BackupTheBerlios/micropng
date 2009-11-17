package micropng;

public class Configuration {

    private String filename;
    private boolean infoMode;
    private boolean verboseMode;
    private boolean removeAncillaryChunks;
    private int[] ancillaryChunksToKeep;
    private int[] ancillaryChunksToRemove;

    public Configuration() {
	filename = null;
	infoMode = false;
	verboseMode = false;
    }

    public void setFilename(String filename) {
	this.filename = filename;
    }

    public String getFilename() {
	return new String(filename);
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

    public boolean isRemoveAncillaryChunks() {
	return removeAncillaryChunks;
    }

    public void setRemoveAncillaryChunks(boolean removeAncillaryChunks) {
	this.removeAncillaryChunks = removeAncillaryChunks;
    }
}
