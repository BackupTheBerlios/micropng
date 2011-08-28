package micropng.userinterface;

import java.util.Hashtable;

public class InternalConfiguration {

    private Hashtable<String, InputConfigurationOption> options;

    private boolean removeAncillaryChunksSwitch;
    private String[] ancillaryChunksToDrop;
    private String[] ancillaryChunksToKeep;
    private boolean aggregateIDATSwitch;
    private boolean optimizeHuffmanTreesSwitch;

    public enum Preset {
	NOOP, DEFAULT;
	static {
	    NOOP.myConfig = new InternalConfiguration();
	    DEFAULT.myConfig = new InternalConfiguration();

	    NOOP.myConfig.aggregateIDATSwitch = false;
	    NOOP.myConfig.optimizeHuffmanTreesSwitch = false;
	    NOOP.myConfig.removeAncillaryChunksSwitch = false;

	    DEFAULT.myConfig.aggregateIDATSwitch = true;
	    DEFAULT.myConfig.optimizeHuffmanTreesSwitch = true;
	    DEFAULT.myConfig.removeAncillaryChunksSwitch = false;
	}
	private InternalConfiguration myConfig;
    }

    private InternalConfiguration() {
	
    }

    public InternalConfiguration(String[] args) {
    }
    
    public static InternalConfiguration createNewConfig(Preset p) {
	InternalConfiguration res;
	try {
	    res = (InternalConfiguration) p.myConfig.clone();
	} catch (CloneNotSupportedException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e.getCause());
	}
	return res;
    }
}
