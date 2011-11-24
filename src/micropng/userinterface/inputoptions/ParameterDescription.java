package micropng.userinterface.inputoptions;

public interface ParameterDescription extends Description {

    public String getLongHelp();

    public String getLongParameterName();

    public String getShortHelp();

    public char getShortParameterName();
}
