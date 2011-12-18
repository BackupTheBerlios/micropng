package micropng.userinterface.inputoptions;

import java.io.File;
import java.io.IOException;

import micropng.commonlib.Status;

public class Path implements ParameterValue<File>, Cloneable {
    private File value;

    public Path() {
	this.value = null;
    }

    @Override
    public ValueType getType() {
	return ValueType.PATH;
    }

    @Override
    public File getValue() {
	return value;
    }

    @Override
    public String toString() {
	return (value == null)? "" : value.toString();
    }

    @Override
    public Status trySetting(File value) {
	if (value == null) {
	    this.value = null;
	} else {
	    try {
		if (!value.getCanonicalFile().isFile()) {
		    return Status.error("der Pfad führt zu keiner Datei");
		}
	    } catch (IOException e) {
		return Status.error("das System meldete einen Fehler beim Zugriff auf den Pfad: „"
			+ e.getMessage() + "“");
	    }
	    this.value = value.getAbsoluteFile();
	}
	return Status.ok();
    }

    @Override
    public Path clone() {
	Path res = null;
	try {
	    res = (Path) super.clone();
	} catch (CloneNotSupportedException e) {
	    e.printStackTrace();
	    System.exit(-1);
	}
	res.value = (value == null)? null : new File(value.getPath());
	return res;
    }
}
