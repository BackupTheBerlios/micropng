package micropng.userinterface.inputoptions;

public interface ParameterDescription {

    public String getLongHelp();

    public String getLongParameterName();
    
    public ParameterGroup getParentGroup();

    public String getShortHelp();

    public char getShortParameterName();
}
