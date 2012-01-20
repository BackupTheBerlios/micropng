package micropng.userinterface.inputoptions;

import java.util.ArrayList;

public enum CoreGroup {
    BASE, FILE_VIEW;

    private static ParameterGroup base;
    private static ParameterGroup fileView;
    private static Parameter condenseIDATChunks = new Parameter(new CondenseIDATChunks());
    private static Parameter sortChunks = new Parameter(new SortChunks());
    private static Parameter removeUselessSBIT = new Parameter(new RemoveUselessSBIT());
    private static Parameter inputFilename = new Parameter(new InputFilename());
    private static Parameter outputFilename = new Parameter(new OutputFilename());
    private static Parameter operationalMode = new Parameter(new OperationalMode());
    private static Parameter userInterface = new Parameter(new UserInterface());
    private static Parameter verbose = new Parameter(new Verbose());

    static {

	String fileViewName = "Sicht auf die gesamte Datei";
	ArrayList<Parameter> fileViewParameters = new ArrayList<Parameter>();
	fileViewParameters.add(condenseIDATChunks);
	fileViewParameters.add(sortChunks);
	fileViewParameters.add(removeUselessSBIT);
	
	ArrayList<ParameterGroup> fileViewSubgroups = new ArrayList<ParameterGroup>();
	fileView = new ParameterGroup(fileViewName, fileViewParameters, fileViewSubgroups);

	String baseGroupName = "allgemeine Steuerung des Programms";
	ArrayList<Parameter> baseGroupParameters = new ArrayList<Parameter>();
	baseGroupParameters.add(inputFilename);
	baseGroupParameters.add(outputFilename);
	baseGroupParameters.add(operationalMode);
	baseGroupParameters.add(userInterface);
	baseGroupParameters.add(verbose);
	ArrayList<ParameterGroup> baseGroupSubgroups = new ArrayList<ParameterGroup>();
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
