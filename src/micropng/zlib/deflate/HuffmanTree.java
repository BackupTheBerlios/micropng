package micropng.zlib.deflate;

public class HuffmanTree {
    private class HuffmanNode {

	private int value;
	private HuffmanNode left;
	private HuffmanNode right;

	public HuffmanNode() {

	}

	public int getValue() {
	    return value;
	}

	public void setValue(int value) {
	    this.value = value;
	}

	public HuffmanNode getLeft() {
	    return left;
	}

	public void setLeft(HuffmanNode left) {
	    this.left = left;
	}

	public HuffmanNode getRight() {
	    return right;
	}

	public void setRight(HuffmanNode right) {
	    this.right = right;
	}

    }

    public class HuffmanTreeWalker {
	private HuffmanNode pos;

	private HuffmanTreeWalker() {
	    reset();
	}

	public void reset() {
	    pos = root;
	}

	public boolean isLeaf() {
	    return (pos.left == null) && (pos.right == null);
	}

	public void step(int bit) {
	    if (bit == 0) {
		pos = pos.left;
	    } else {
		pos = pos.right;
	    }
	}

	public int getValue() {
	    return pos.getValue();
	}
    }

    private static final int MAX_BITS = 15;

    private HuffmanNode root;

    public HuffmanTree(int[] codeLengths) {
	root = new HuffmanNode();
	int[] bl_count = new int[MAX_BITS + 1];
	int[] next_code = new int[MAX_BITS + 1];
	int code = 0;

	for (int i : codeLengths) {
	    bl_count[i]++;
	}

	bl_count[0] = 0;

	for (int bits = 1; bits <= MAX_BITS; bits++) {
	    code = (code + bl_count[bits - 1]) << 1;
	    next_code[bits] = code;
	}

	for (int n = 0; n < codeLengths.length; n++) {
	    int len = codeLengths[n];
	    if (len != 0) {
		addCode(n, next_code[len], len);
		next_code[len]++;
	    }
	}
    }

    private void addCode(int value, int code, int length) {
	HuffmanNode currentNode = root;
	int mask = 0x01 << length;
	for (int i = 0; i < length; i++) {
	    HuffmanNode nextNode;
	    boolean leftTree;
	    mask >>= 1;
	    leftTree = ((mask & code) == 0);

	    if (leftTree) {
		nextNode = currentNode.getLeft();
		if (nextNode == null) {
		    nextNode = new HuffmanNode();
		    currentNode.setLeft(nextNode);
		}
	    } else {
		nextNode = currentNode.getRight();
		if (nextNode == null) {
		    nextNode = new HuffmanNode();
		    currentNode.setRight(nextNode);
		}
	    }
	    currentNode = nextNode;
	}
	currentNode.setValue(value);
    }

    public HuffmanTreeWalker getHuffmanTreeWalker() {
	HuffmanTreeWalker res = new HuffmanTreeWalker();
	return res;
    }
}
