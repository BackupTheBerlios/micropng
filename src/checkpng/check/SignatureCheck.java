package checkpng.check;

import java.io.IOException;
import java.io.RandomAccessFile;

import micropng.fileview.PNGProperties;

public abstract class SignatureCheck extends Check {
    public SignatureCheck(CheckTask task) {
	super(task);
	// TODO Auto-generated constructor stub
    }

    public void readSignature(RandomAccessFile input) throws IOException {
	for (byte b : PNGProperties.getSignature()) {
	    int next = input.read();
	    if (next == -1) {
		error("End of file before end of PNG signature.");
		return;
	    }
	    if (((byte) next) != b) {
		error("Bad PNG signature. Is this really a png file?");
		return;
	    }
	}
    }
}
