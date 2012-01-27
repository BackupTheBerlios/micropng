package micropng.userinterface;

import java.io.File;
import java.io.IOException;

import micropng.chunkview.ChunkSequence;
import micropng.commonlib.Status;
import micropng.micropng.Optimizer;
import micropng.pngio.FileWriter;

public class Runner {

    public Status runActualProgram(UserConfiguration inputConfiguration) {
	Status res;
	Configurator configurator = new Configurator();
	InternalConfiguration internalConfiguration;

	try {
	    internalConfiguration = configurator.makeActualConfig(inputConfiguration);
	} catch (IOException e) {
	    e.printStackTrace();
	    return Status
		    .error("Eine IOException ist aufgetreten. Dieses Problem ließ sich nicht beheben.");
	}
	res = configurator.getLastStatus();

	if (res.getStatusType() == Status.StatusType.OK) {
	    FileWriter fileWriter = new FileWriter();
	    File outputFile = internalConfiguration.getOutputFile();
	    ChunkSequence finalSequence;
	    Optimizer optimizer = new Optimizer();
	    // finalSequence = optimizer.optimize(internalConfiguration);
	    // fileWriter.writeSequence(outputFile, finalSequence);
	}

	return res;
    }

    public Status launchInterface(UserConfiguration inputConfiguration) {
	inputConfiguration.getByLongName("user-interface");
	return runActualProgram(inputConfiguration);
    }
}
