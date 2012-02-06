package micropng.userinterface;

import java.util.HashMap;
import java.util.Iterator;

import micropng.userinterface.inputoptions.Parameter;
import micropng.userinterface.inputoptions.ParameterGroup;

public class UserConfiguration implements Iterable<Parameter> {
 
    private final ParameterGroup myGroup;
    private final HashMap<String, Parameter> longParametersTable;
    private final HashMap<Character, Parameter> shortParametersTable;

    public UserConfiguration(ParameterGroup parameterGroup) {
	myGroup = parameterGroup;
	longParametersTable = new HashMap<String, Parameter>();
	shortParametersTable = new HashMap<Character, Parameter>();
	for (Parameter parameter : myGroup) {
	    final String keyLongParameter = parameter.getLongParameterName();
	    final char keyShortParameter = parameter.getShortParameterName();

	    if (longParametersTable.containsKey(keyLongParameter)) {
		throw new DuplicateParameterAssignment();
	    }
	    if (shortParametersTable.containsKey(keyShortParameter)) {
		throw new DuplicateParameterAssignment();
	    }

	    longParametersTable.put(keyLongParameter, parameter);
	    shortParametersTable.put(keyShortParameter, parameter);
	}
    }

    public Parameter getByLongName(String longName) {
	return longParametersTable.get(longName);
    }

    public Parameter getByShortName(char shortName) {
	return longParametersTable.get(shortName);
    }

    @Override
    public Iterator<Parameter> iterator() {
	return myGroup.iterator();
    }
}
