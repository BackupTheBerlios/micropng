package micropng.micropng;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import micropng.chunkview.ChunkSequence;
import micropng.pngio.FileReader;
import micropng.userinterface.InternalConfiguration;
import micropng.userinterface.OutputChannel;

public class Configurator {
    private ArrayList<ConfigurationListener> listeners = new ArrayList<ConfigurationListener>();

    public InternalConfiguration makeActualConfig(UserConfiguration userConf) throws IOException {
	InternalConfiguration res = null;
	String filePath = userConf.getPath();
	File targetFile = new File(filePath);
	FileReader reader;
	ChunkSequence chunkSequence;

	if (!targetFile.isFile()) {
	    message(OutputChannel.ERROR, "no such file: " + filePath);
	    return null;
	}

	reader = new FileReader();
	chunkSequence = reader.readSequence(targetFile);

	// TODO

	// check for unknown mandatory chunks -> set config: do not do anything

	// check for unknown non-mandatory unsafe-to-copy chunks -> set config:
	// no manipulation of mandatory chunks

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
