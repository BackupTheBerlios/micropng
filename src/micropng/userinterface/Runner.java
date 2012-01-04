package micropng.userinterface;

import java.io.IOException;

import micropng.commonlib.Status;

public class Runner {

    public Status runActualProgram(UserConfiguration inputConfiguration) {
	Status res;
	Configurator configurator = new Configurator();
	InternalConfiguration internalConfiguration = new InternalConfiguration();
	try {
	    res = configurator.makeActualConfig(inputConfiguration, internalConfiguration);
	    if (res.getStatusType() == Status.StatusType.ERROR) {
		return res;
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	    return Status.error("");
	}
	return Status.ok();
    }
    
    public Status launchInterface(UserConfiguration inputConfiguration) {
	inputConfiguration.getByLongName("user-interface");
	return runActualProgram(inputConfiguration);
    }
}
