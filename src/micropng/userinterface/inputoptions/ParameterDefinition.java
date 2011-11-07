package micropng.userinterface.inputoptions;

import java.util.ArrayList;

import micropng.commonlib.Status;

public interface ParameterDefinition {

    public String getLongHelp();

    public String getLongParameterName();

    public String getShortHelp();

    public char getShortParameterName();

    public Status validateAndSet(ArrayList<String> values);
}
