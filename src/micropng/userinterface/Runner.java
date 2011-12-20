package micropng.userinterface;

import java.io.IOException;

import micropng.commonlib.Status;

public class Runner {

    public Status runActualProgram(UserConfiguration inputConfiguration) {
	Configurator configurator = new Configurator();
	try {
	    configurator.makeActualConfig(inputConfiguration);
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
