package micropng.micropng.inputoptions;

import java.util.ArrayList;

import micropng.userinterface.inputoptions.CondenseIDATChunks;
import micropng.userinterface.inputoptions.InputFilename;
import micropng.userinterface.inputoptions.MissingDefinitionException;
import micropng.userinterface.inputoptions.OutputFilename;
import micropng.userinterface.inputoptions.Parameter;
import micropng.userinterface.inputoptions.ParameterGroup;
import micropng.userinterface.inputoptions.RemoveUselessSBIT;
import micropng.userinterface.inputoptions.SortChunks;

public enum CoreGroup {
    BASE, FILE_VIEW;

    private final static ParameterGroup base;
    private final static ParameterGroup fileView;
    private final static Parameter condenseIDATChunks = new Parameter(new CondenseIDATChunks());
    private final static Parameter sortChunks = new Parameter(new SortChunks());
    private final static Parameter removeUselessSBIT = new Parameter(new RemoveUselessSBIT());
    private final static Parameter inputFilename = new Parameter(new InputFilename());
    private final static Parameter outputFilename = new Parameter(new OutputFilename());
    //private final static Parameter operationalMode = new Parameter(new OperationalMode());
    //private final static Parameter userInterface = new Parameter(new UserInterface());
    //private final static Parameter verbose = new Parameter(new Verbose());

    static {

	final String fileViewName = "Sicht auf die gesamte Datei";
	final ArrayList<Parameter> fileViewParameters = new ArrayList<Parameter>();

	fileViewParameters.add(condenseIDATChunks);
	fileViewParameters.add(sortChunks);
	fileViewParameters.add(removeUselessSBIT);
	
	final ArrayList<ParameterGroup> fileViewSubgroups = new ArrayList<ParameterGroup>();
	fileView = new ParameterGroup(fileViewName, fileViewParameters, fileViewSubgroups);

	final String baseGroupName = "allgemeine Steuerung des Programms";
	final ArrayList<Parameter> baseGroupParameters = new ArrayList<Parameter>();
	
	baseGroupParameters.add(inputFilename);
	baseGroupParameters.add(outputFilename);
	//baseGroupParameters.add(operationalMode);
	//baseGroupParameters.add(userInterface);
	//baseGroupParameters.add(verbose);

	final ArrayList<ParameterGroup> baseGroupSubgroups = new ArrayList<ParameterGroup>();
	baseGroupSubgroups.add(fileView);
	base = new ParameterGroup(baseGroupName, baseGroupParameters, baseGroupSubgroups);
    }

    public ParameterGroup getGroup() {
	switch (this) {
	case BASE:
	    return base;
	case FILE_VIEW:
	    return fileView;
	}
	throw new MissingDefinitionException();
    }
}
