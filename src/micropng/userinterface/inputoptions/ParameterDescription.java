package micropng.userinterface.inputoptions;

import java.util.ArrayList;

import micropng.commonlib.Status;

public interface ParameterDescription {

    public String getLongHelp();

    public String getLongParameterName();
    
    public ParameterGroup getParentGroup();

    public String getShortHelp();

    public char getShortParameterName();

    public Status validateAndSet(ArrayList<String> values);

    public boolean takesArgument();
}
