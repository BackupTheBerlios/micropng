package micropng;

import java.io.IOException;

public class Main {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
	String inputFilename;
	String outputFilename;

	if (args.length != 1) {
	    System.out.println("usage: java micropng.Main <filename>");
	    System.exit(1);
	}

	inputFilename = args[0];
	outputFilename = args[0] + "_output.png";

	Optimizer optimizer = new Optimizer(inputFilename, outputFilename);
	optimizer.run();
    }
}
