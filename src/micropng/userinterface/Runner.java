package micropng.userinterface;

import java.io.IOException;

import micropng.commonlib.Status;
import micropng.micropng.Optimizer;

public class Runner {

    public Status runActualProgram(UserConfiguration inputConfiguration) {
	Status res;
	Configurator configurator = new Configurator();
	InternalConfiguration internalConfiguration;
	Optimizer optimizer;
	try {
	    internalConfiguration = configurator.makeActualConfig(inputConfiguration);
	    res = configurator.getLastStatus();
	    if (res.getStatusType() == Status.StatusType.OK) {
		optimizer = new Optimizer(internalConfiguration);
		optimizer.run();
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	    return Status
		    .error("Eine IOException ist aufgetreten. Dieses Problem ließ sich nicht beheben.");
	}
	return res;
    }

    public Status launchInterface(UserConfiguration inputConfiguration) {
	inputConfiguration.getByLongName("user-interface");
	return runActualProgram(inputConfiguration);
    }
}
