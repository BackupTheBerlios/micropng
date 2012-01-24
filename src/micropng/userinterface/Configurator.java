package micropng.userinterface;

import java.io.File;
import java.io.IOException;
//import java.util.ArrayList;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.Type;
import micropng.commonlib.Status;
//import micropng.micropng.ConfigurationListener;
//import micropng.micropng.ContentAnalyzer;
//import micropng.micropng.FullIDATDecoder;
import micropng.pngio.FileReader;
import micropng.userinterface.inputoptions.Parameter;

public class Configurator {
    private Status lastStatus;
    //private ArrayList<ConfigurationListener> listeners = new ArrayList<ConfigurationListener>();

    public Configurator() {
	lastStatus = Status.ok();
    }

    public Status getLastStatus() {
	return lastStatus;
    }

    private void setLastStatus(Status status) {
	this.lastStatus = status;
    }
    
    public InternalConfiguration makeActualConfig(UserConfiguration userConf) throws IOException {
	InternalConfiguration res = new InternalConfiguration();
	Parameter filePath = userConf.getByLongName("input-file");
	File inputFile = filePath.<File>take();
	FileReader reader = new FileReader();
	ChunkSequence chunkSequence;
//	FullIDATDecoder decoder;
//	ContentAnalyzer contentAnalyzer = new ContentAnalyzer();

	if (!inputFile.isFile()) {
	    setLastStatus(Status.error("Der Pfad „" + inputFile + "“ zeigt auf keine normale Datei."));
	}

	if (!inputFile.canRead()) {
	    setLastStatus(Status.error("Die Datei „" + inputFile + "“ kann nicht gelesen werden."));
	}

	chunkSequence = reader.readSequence(inputFile);
	res.setChunkSequence(chunkSequence);

	for (Chunk c : chunkSequence) {
	    int type = c.getType();
	    if (!Type.isKnown(type)) {
		if (Type.isAncillary(type)) {
		    if (!Type.isSafeToCopy(type)) {
//			if (ancillaryChunkShallBeKept(userConf, type)) {
			    res.setUnknownAncillaryChunk(true);
//			}
		    }
		} else {
		    res.setUnknownMandatoryChunk(true);
		}
	    }
	}

//	decoder = new FullIDATDecoder(chunkSequence);
//	decoder.decode();

	setLastStatus(Status.ok());
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

//    public void addConfigurationListener(ConfigurationListener listener) {
//	listeners.add(listener);
//    }
//
//    public void removeConfigurationListener(ConfigurationListener listener) {
//	listeners.remove(listener);
//    }
}
