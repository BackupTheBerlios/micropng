package micropng.userinterface.invocationline;

import java.util.ArrayList;

import micropng.commonlib.Status;

public interface ValueParser {
    public Status parseValue(ArrayList<String> input);
}
