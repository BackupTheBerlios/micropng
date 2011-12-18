package micropng.userinterface;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.Type;
import micropng.micropng.ConfigurationListener;
import micropng.pngio.FileReader;

public class Configurator {
    private ArrayList<ConfigurationListener> listeners = new ArrayList<ConfigurationListener>();

    public InternalConfiguration makeActualConfig(UserConfiguration userConf) throws IOException {
	InternalConfiguration res;
	//File filePath = userConf.getPath();
	//File targetFile = new File(filePath);
	FileReader reader;
	ChunkSequence chunkSequence;

//	if (!targetFile.isFile()) {
//	    message(OutputChannel.ERROR, "no such file: " + filePath);
//	    return null;
//	}

	res = new InternalConfiguration();
	reader = new FileReader();
//	chunkSequence = reader.readSequence(targetFile);
//	res.setChunkSequence(chunkSequence);

//	for (Chunk c : chunkSequence) {
//	    int type = c.getType();
//	    if (!Type.isKnown(type)) {
//		if (Type.isAncillary(type)) {
//		    if (!Type.isSafeToCopy(type)) {
//			if (ancillaryChunkShallBeKept(userConf, type)) {
//			    res.setUnknownAncillaryChunkInResult(true);
//			}
//		    }
//		} else {
//		    res.setUnknownMandatoryChunkInResult(true);
//		}
//	    }
//	}

	return res;
    }

//    private boolean ancillaryChunkShallBeKept(UserConfiguration userConf, int type) {
//	boolean res;
//	if (userConf.doesRemoveAncillaryChunks()) {
//	    res = false;
//	    int[] chunksToKeep = userConf.getAncillaryChunksToKeep();
//	    for (int i : chunksToKeep) {
//		if (type == i) {
//		    res = true;
//		}
//	    }
//	} else {
//	    res = true;
//	    int[] chunksToRemove = userConf.getAncillaryChunksToRemove();
//	    for (int i : chunksToRemove) {
//		if (type == i) {
//		    res = false;
//		}
//	    }
//	}
//	return res;
//    }

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
