package micropng.userinterface;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.Type;
import micropng.commonlib.Status;
import micropng.micropng.ConfigurationListener;
import micropng.micropng.EncodingAnalyzer;
import micropng.pngio.FileReader;
import micropng.userinterface.inputoptions.Parameter;

public class Configurator {
    private ArrayList<ConfigurationListener> listeners = new ArrayList<ConfigurationListener>();

    public Status makeActualConfig(UserConfiguration userConf, InternalConfiguration internalConfiguration) throws IOException {
	Parameter filePath = userConf.getByLongName("input-file");
	File inputFile = filePath.<File>take();
	FileReader reader = new FileReader();
	ChunkSequence chunkSequence;
	EncodingAnalyzer encodingAnalyzer = new EncodingAnalyzer();

	if (!inputFile.isFile()) {
	    return Status.error("Der Pfad „" + inputFile + "“ zeigt auf keine normale Datei.");
	}

	if (!inputFile.canRead()) {
	    return Status.error("Die Datei „" + inputFile + "“ kann nicht gelesen werden.");
	}

	chunkSequence = reader.readSequence(inputFile);
	internalConfiguration.setChunkSequence(chunkSequence);

	for (Chunk c : chunkSequence) {
	    int type = c.getType();
	    if (!Type.isKnown(type)) {
		if (Type.isAncillary(type)) {
//		    if (!Type.isSafeToCopy(type)) {
//			if (ancillaryChunkShallBeKept(userConf, type)) {
//			    internalConfiguration.setUnknownAncillaryChunkInResult(true);
//			}
		    }
		} else {
		    internalConfiguration.setUnknownMandatoryChunkInResult(true);
		}
//	    }
	}

	encodingAnalyzer.analyze(chunkSequence);

	return Status.ok();
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
}
