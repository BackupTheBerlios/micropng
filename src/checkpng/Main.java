/*
 * Copyright 2009-2013 Martin Walch
 * redistributable under the terms of the GNU GPL version 3
 */

//TODO: Merge checkpng and micropng. Use enums for implementation selection. 

package checkpng;

import checkpng.inputoptions.CoreGroup;
import micropng.userinterface.UserConfiguration;
import micropng.userinterface.invocationline.InvocationLineEvaluator;

public class Main {
    public static void main(String[] args) {
	UserConfiguration userConfiguration = new InvocationLineEvaluator(CoreGroup.BASE.getGroup()).evaluate(args);
    }
}
