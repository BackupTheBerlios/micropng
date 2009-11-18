package micropng;

import java.util.ArrayList;
import java.util.Iterator;

import micropng.chunk.Type;

public class PartialOrderRelationPost {

    private Type type;
    private PartialOrderRelationPost post;

    public PartialOrderRelationPost(int type) {
	this.type = new Type(type);
    }

    public void init(OptimizerOrdering optimizer) {
	post = optimizer.getTypeHandler(type.toInt());
    }

    public int compareTo(PartialOrderRelationPost relation) {
	ArrayList<PartialOrderRelationPost> myRelationList = getRelationList();
	ArrayList<PartialOrderRelationPost> otherRelationList = relation.getRelationList();
	Iterator<PartialOrderRelationPost> myIterator = myRelationList.iterator();
	Iterator<PartialOrderRelationPost> otherIterator = otherRelationList.iterator();

	while (myIterator.hasNext() && otherIterator.hasNext()) {
	    PartialOrderRelationPost my = myIterator.next();
	    PartialOrderRelationPost other = otherIterator.next();
	    if (my.type.toInt() < other.type.toInt()) {
		return -1;
	    }
	    if (my.type.toInt() > other.type.toInt()) {
		return 1;
	    }
	}
	if (otherIterator.hasNext()) {
	    return -1;
	}
	if (myIterator.hasNext()) {
	    return 1;
	}
	return 0;
    }

    public int getType() {
	return type.toInt();
    }

    public ArrayList<PartialOrderRelationPost> getRelationList() {
	if (post == null) {
	    ArrayList<PartialOrderRelationPost> res = new ArrayList<PartialOrderRelationPost>();
	    res.add(this);
	    return res;
	} else {
	    ArrayList<PartialOrderRelationPost> inheritedList = post.getRelationList();
	    inheritedList.add(this);
	    return inheritedList;
	}
    }
}
