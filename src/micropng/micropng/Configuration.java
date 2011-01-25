package micropng.micropng;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import micropng.chunkview.ChunkSequence;
import micropng.pngio.FileReader;
import micropng.userinterface.OutputChannel;

public class Configuration implements Cloneable {
    private class Configurator {
	private ArrayList<ConfigurationListener> listeners = new ArrayList<ConfigurationListener>();

	public Configuration sanitize() throws IOException {
	    Configuration res = Configuration.createNewConfig(Preset.NOOP);
	    File targetFile = new File(getPath());
	    FileReader reader;

	    if (!targetFile.isFile()) {
		message(OutputChannel.ERROR, "no such file: " + getPath());
		return null;
	    }

	    res.setPath(getPath());

	    reader = new FileReader(targetFile);
	    chunkSequence = reader.readSequence();

	    //TODO
	    
	    return res;
	}

	public void addConfigurationListener(ConfigurationListener listener) {
	    listeners.add(listener);
	}

	public void removeConfigurationListener(ConfigurationListener listener) {
	    listeners.remove(listener);
	}

	private void message(OutputChannel channel, String message) {
	    for (ConfigurationListener listener : listeners) {
		listener.configurationMessage(channel, message);
	    }
	}
    }

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
    private ChunkSequence chunkSequence;

    private Configuration() {
    }

    public static Configuration createNewConfig(Preset p) {
	Configuration res;
	try {
	    res = (Configuration) p.myConfig.clone();
	} catch (CloneNotSupportedException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e.getCause());
	}
	return res;
    }

    public Configuration generateInitiatedConfiguration() throws IOException {
	Configurator configurator = new Configurator();
	Configuration res = configurator.sanitize();
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
