package micropng.userinterface.inputoptions;

import java.io.File;
import java.io.IOException;

import micropng.commonlib.Status;

public class Path implements ParameterValue<File> {

    private File value;

    public Path() {
	this.value = null;
    }

    @Override
    public File getValue() {
	return value;
    }

    @Override
    public Status trySetting(File value) {
	try {
	    if (!value.getCanonicalFile().isFile()) {
		return Status.error("der Pfad führt zu keiner Datei");
	    }
	} catch (IOException e) {
	    return Status.error("das System meldete einen Fehler beim Zugriff auf den Pfad: „" + e.getMessage() + "“");
	}
	value = value.getAbsoluteFile();
	return Status.ok();
    }
}
