package micropng.userinterface.inputoptions;

public enum CoreGroup {
    BASE, FILE_VIEW;

    private static ParameterGroup base;
    private static ParameterGroup fileView;

    static {
	String fileViewName = "Sicht auf die gesamte Datei";
	Parameter[] fileViewParameters = new Parameter[] { new Parameter(new CondenseIDATChunks()),
		new Parameter(new SortChunks()) };
	ParameterGroup[] fileViewSubgroups = new ParameterGroup[] {};
	fileView = new ParameterGroup(fileViewName, fileViewParameters, fileViewSubgroups);

	String baseGroupName = "allgemeine Steuerung des Programms";
	Parameter[] baseGroupParameters = new Parameter[] { new Parameter(new InputFilename()),
		new Parameter(new OutputFilename()), new Parameter(new Verbose()) };
	ParameterGroup[] baseGroupSubgroups = new ParameterGroup[] { fileView };
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