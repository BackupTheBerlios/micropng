package micropng.micropng;

public class Configuration implements Cloneable {

    enum Preset {
	NOOP, DEFAULT;
	static {
	    NOOP.myConfig = new Configuration();
	    DEFAULT.myConfig = new Configuration();

	    NOOP.myConfig.filename = null;
	    NOOP.myConfig.infoMode = true;
	    NOOP.myConfig.verboseMode = false;
	    NOOP.myConfig.removeAncillaryChunks = false;

	    DEFAULT.myConfig.filename = null;
	    DEFAULT.myConfig.infoMode = false;
	    DEFAULT.myConfig.verboseMode = false;
	    DEFAULT.myConfig.removeAncillaryChunks = false;

	}
	private Configuration myConfig;
    }

    private String filename;
    private boolean infoMode;
    private boolean verboseMode;
    private boolean removeAncillaryChunks;
    private int[] ancillaryChunksToKeep;
    private int[] ancillaryChunksToRemove;

    private Configuration() {
    }

    @Override
    public Configuration clone() {
	Configuration res = null;
	try {
	    res = (Configuration) super.clone();
	} catch (CloneNotSupportedException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e.getCause());
	}
	return res;
    }

    public Configuration createNewConfig(Preset p) {
	return (Configuration) p.myConfig.clone();
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
