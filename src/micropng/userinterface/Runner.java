package micropng.userinterface;

import java.io.IOException;

import micropng.commonlib.Status;

public class Runner {

    public Status runActualProgram(UserConfiguration inputConfiguration) {
	Configurator configurator = new Configurator();
	InternalConfiguration internalConfiguration = new InternalConfiguration();
	try {
	    configurator.makeActualConfig(inputConfiguration, internalConfiguration);
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
