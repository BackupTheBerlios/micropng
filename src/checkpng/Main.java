/*
 * Copyright 2009-2012 Martin Walch
 * redistributable under the terms of the GNU GPL version 3
 */

package checkpng;

import checkpng.inputoptions.CoreGroup;
import micropng.userinterface.UserConfiguration;
import micropng.userinterface.invocationline.InvocationLineEvaluator;

public class Main {
    public static void main(String[] args) {
	UserConfiguration userConfiguration = new InvocationLineEvaluator(CoreGroup.BASE.getGroup()).evaluate(args);
    }
}
