/*
 * Copyright 2009-2012 Martin Walch
 * redistributable under the terms of the GNU GPL version 3
 */

package micropng;

import micropng.commonlib.Status;
import micropng.userinterface.Runner;
import micropng.userinterface.UserConfiguration;
import micropng.userinterface.invocationline.InvocationLineEvaluator;

public class Main {
    public static void main(String[] args) {
	UserConfiguration userConfiguration = new InvocationLineEvaluator().evaluate(args);
	Status status = new Runner().launchInterface(userConfiguration);
	if (status.getStatusType() == Status.StatusType.ERROR) {
	    System.err.println(status.message());
	    System.exit(-1);
	}
    }
}
