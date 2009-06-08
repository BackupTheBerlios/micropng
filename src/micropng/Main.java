package micropng;

import java.io.IOException;

public class Main {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
	String inputFilename;

	if (args.length != 1) {
	    System.out.println("usage: java micropng.Main <filename>");
	    System.exit(1);
	}

	inputFilename = args[0];

	Optimizer optimizer = new Optimizer(inputFilename);
	optimizer.run();
    }
}
