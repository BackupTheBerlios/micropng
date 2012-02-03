package checkpng.inputoptions;

import java.util.ArrayList;

import micropng.userinterface.inputoptions.InputFilename;
import micropng.userinterface.inputoptions.MissingDefinitionException;
import micropng.userinterface.inputoptions.Parameter;
import micropng.userinterface.inputoptions.ParameterGroup;

public enum CoreGroup {
    BASE, FILE_VIEW;

    private static ParameterGroup base;
    private static Parameter inputFilename = new Parameter(new InputFilename());

    static {
	String baseGroupName = "allgemeine Steuerung des Programms";
	ArrayList<Parameter> baseGroupParameters = new ArrayList<Parameter>();
	baseGroupParameters.add(inputFilename);
	ArrayList<ParameterGroup> baseGroupSubgroups = new ArrayList<ParameterGroup>();
	base = new ParameterGroup(baseGroupName, baseGroupParameters, baseGroupSubgroups);
    }

    public ParameterGroup getGroup() {
	switch (this) {
	case BASE:
	    return base;
	}

	throw new MissingDefinitionException();
    }
}
