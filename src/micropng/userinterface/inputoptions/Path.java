package micropng.userinterface.inputoptions;

import java.io.File;

import micropng.commonlib.Status;

public class Path implements ParameterValue<File> {
    private File value = new File("");

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
	return (value == null) ? "" : value.toString();
    }

    @Override
    public Status trySetting(File value) {
	this.value = value.getAbsoluteFile();
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
	res.value = (value == null) ? null : new File(value.getPath());
	return res;
    }
}
