package micropng.userinterface.inputoptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class ParameterGroup implements Iterable<Parameter> {
    private class ParameterIterator implements java.util.Iterator<Parameter> {
	private Iterator<Parameter> iterator;

	private ParameterIterator() {
	    iterator = getAllDependingParameters().iterator();
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

    public ParameterGroup(String name, Parameter[] parameters,
	    ParameterGroup[] subGroups) {
	this.name = name;
	this.parameters = new ArrayList<Parameter>();
	Collections.addAll(this.parameters, parameters);
	this.subGroups = new ArrayList<ParameterGroup>();
	Collections.addAll(this.subGroups, subGroups);
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

    private ArrayList<Parameter> getAllDependingParameters() {
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
