package micropng.userinterface.inputoptions;

import java.util.ArrayList;
import java.util.Iterator;

public class ParameterGroup implements Iterable<Parameter> {
    private class ParameterIterator implements java.util.Iterator<Parameter> {
	private Iterator<Parameter> iterator = getAllDependingParameters().iterator();

	ParameterIterator() {
	}

	@Override
	public boolean hasNext() {
	    return iterator.hasNext();
	}

	@Override
	public Parameter next() {
	    return iterator.next();
	}

	@Override
	public void remove() {
	    throw new UnsupportedOperationException();
	}
    }

    private String name;
    private ArrayList<Parameter> parameters;
    private ArrayList<ParameterGroup> subGroups;

    public ParameterGroup(String name, ArrayList<Parameter> parameters,
	    ArrayList<ParameterGroup> subGroups) {
	this.name = name;
	this.parameters = parameters;
	this.subGroups = subGroups;
    }

    public String getName() {
	return name;
    }

    public ArrayList<Parameter> getParameters() {
	return parameters;
    }

    public ArrayList<ParameterGroup> getSubGroups() {
	return subGroups;
    }

    final ArrayList<Parameter> getAllDependingParameters() {
	ArrayList<Parameter> res = new ArrayList<Parameter>();
	res.addAll(parameters);

	for (ParameterGroup g : subGroups) {
	    res.addAll(g.getAllDependingParameters());
	}
	return res;
    }

    @Override
    public Iterator<Parameter> iterator() {
	return new ParameterIterator();
    }
}
