package micropng.zlib.deflate;

public class HuffmanTree {
    private class HuffmanNode {
	final HuffmanNode left;
	final HuffmanNode right;
	final int value;

	HuffmanNode(int value) {
	    this.left = null;
	    this.right = null;
	    this.value = value;
	}

	HuffmanNode(HuffmanNode left, HuffmanNode right) {
	    this.left = left;
	    this.right = right;
	    this.value = 0;
	}
    }

    private class HuffmanNodeDraft {
	HuffmanNodeDraft left;
	HuffmanNodeDraft right;
	int value;
    }

    public class HuffmanTreeWalker {
	private HuffmanNode pos = root;

	public void reset() {
	    pos = root;
	}

	public boolean isLeaf() {
	    // pos.left == null iff pos.right == null iff isLeaf
	    return pos.left == null;
	}

	public void step(int bit) {
	    pos = (bit == 0) ? pos.left : pos.right;
	}

	public int getValue() {
	    return pos.value;
	}
    }

    private static final int MAX_BITS = 15;

    private HuffmanNode root;
    HuffmanNodeDraft rootDraft = new HuffmanNodeDraft();

    public HuffmanTree(int[] codeLengths) {
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
	root = fixSubtree(rootDraft);
    }

    private HuffmanNode fixSubtree(HuffmanNodeDraft draftNode) {
	if (draftNode.left != null) {
	    return new HuffmanNode(fixSubtree(draftNode.left), fixSubtree(draftNode.right));
	} else {
	    return new HuffmanNode(draftNode.value);
	}
    }
    
    private void addCode(int value, int code, int length) {
	HuffmanNodeDraft currentNode = rootDraft;

	int mask = 0x01 << length;
	for (int i = 0; i < length; i++) {
	    final boolean leftTree;
	    HuffmanNodeDraft nextNode;
	    mask >>= 1;
	    leftTree = ((mask & code) == 0);

	    if (leftTree) {
		nextNode = currentNode.left;
		if (nextNode == null) {
		    nextNode = new HuffmanNodeDraft();
		    currentNode.left = nextNode;
		}
	    } else {
		nextNode = currentNode.right;
		if (nextNode == null) {
		    nextNode = new HuffmanNodeDraft();
		    currentNode.right = nextNode;
		}
	    }
	    currentNode = nextNode;
	}

	currentNode.value = value;
    }

    public HuffmanTreeWalker getHuffmanTreeWalker() {
	return new HuffmanTreeWalker();
    }
}
