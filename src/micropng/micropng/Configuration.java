package micropng.micropng;

public class Configuration implements Cloneable {

    public enum Preset {
	NOOP, DEFAULT;
	static {
	    NOOP.myConfig = new Configuration();
	    DEFAULT.myConfig = new Configuration();

	    NOOP.myConfig.path = null;
	    NOOP.myConfig.infoMode = true;
	    NOOP.myConfig.verboseMode = false;
	    NOOP.myConfig.setAggregateIDAT(false);
	    NOOP.myConfig.setOptimizeHuffmanTrees(false);
	    NOOP.myConfig.removeAncillaryChunks = false;

	    DEFAULT.myConfig.path = null;
	    DEFAULT.myConfig.infoMode = false;
	    DEFAULT.myConfig.verboseMode = false;
	    NOOP.myConfig.setAggregateIDAT(true);
	    NOOP.myConfig.setOptimizeHuffmanTrees(true);
	    DEFAULT.myConfig.removeAncillaryChunks = false;

	}
	private Configuration myConfig;
    }

    private String path;
    private boolean infoMode;
    private boolean verboseMode;
    private boolean aggregateIDAT;
    private boolean optimizeHuffmanTrees;
    private boolean removeAncillaryChunks;
    private int[] ancillaryChunksToKeep;
    private int[] ancillaryChunksToRemove;

    private Configuration() {
    }

    public static Configuration createNewConfig(Preset p) {
	Configuration res = null;
	try {
	    res = (Configuration) p.myConfig.clone();
	} catch (CloneNotSupportedException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e.getCause());
	}
	return res;
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

    public boolean isRemoveAncillaryChunks() {
	return removeAncillaryChunks;
    }

    public void setRemoveAncillaryChunks(boolean removeAncillaryChunks) {
	this.removeAncillaryChunks = removeAncillaryChunks;
    }

    public void setAggregateIDAT(boolean aggregateIDAT) {
	this.aggregateIDAT = aggregateIDAT;
    }

    public boolean doAggregateIDAT() {
	return aggregateIDAT;
    }

    public void setOptimizeHuffmanTrees(boolean optimizeHuffmanTrees) {
	this.optimizeHuffmanTrees = optimizeHuffmanTrees;
    }

    public boolean doOptimizeHuffmanTrees() {
	return optimizeHuffmanTrees;
    }
}
