package micropng.micropng;

import java.io.File;
import java.util.ArrayList;

import micropng.chunkview.chunk.Type;
import micropng.micropng.Configuration.Preset;
import micropng.userinterface.OutputChannel;

public class Configurator {

    private ArrayList<ConfiguratorListener> listeners = new ArrayList<ConfiguratorListener>();

    public Configuration sanitize(Configuration userConfiguration) {
	Configuration res = Configuration.createNewConfig(Preset.NOOP);
	File targetFile = new File(userConfiguration.getPath());
	if (!targetFile.isFile()) {
	    message(OutputChannel.ERROR, "file not found: " + userConfiguration.getPath());
	    return null;
	}
	res.setPath(userConfiguration.getPath());
	return res;
    }

    public void addConfigurationListener(ConfiguratorListener listener) {
	listeners.add(listener);
    }

    public void removeConfigurationListener(ConfiguratorListener listener) {
	listeners.remove(listener);
    }

    private void message(OutputChannel channel, String message) {
	for (ConfiguratorListener listener : listeners) {
	    listener.configurationMessage(channel, message);
	}
    }
}
