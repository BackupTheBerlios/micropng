package micropng.userinterface;

import micropng.commonlib.Status;

public class Runner {
    public Status launch(UserConfiguration inputConfiguration) {
	inputConfiguration.getByLongName("");
	return Status.ok();
    }
}
