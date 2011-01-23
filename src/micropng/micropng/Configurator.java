package micropng.micropng;

import micropng.chunkview.chunk.Type;
import micropng.micropng.Configuration.Preset;

public class Configurator {

    public Configuration sanitize(Configuration userConfiguration) {
	Configuration res = Configuration.createNewConfig(Preset.NOOP);
	res.setFilename(userConfiguration.getFilename());
	return res;
    }
}
