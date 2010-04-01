package micropng;

import java.io.IOException;

import micropng.cli.CommandLineParser;
import micropng.cli.OutputChannel;
import micropng.cli.WrongUsageException;
import micropng.pngoptimization.Optimizer;


public class Main {

    OutputChannel out;
    String[] args;

    public Main(String[] args) {
	out = new OutputChannel();
	this.args = args;
    }

    public void run() throws IOException {
	Optimizer optimizer = new Optimizer(configure());
	optimizer.run();
    }

    private Configuration configure() {
	Configuration res = null;
	CommandLineParser parser = new CommandLineParser(args);
	try {
	    res = parser.parse();
	} catch (WrongUsageException e) {
	    out.error("usage: java micropng.Main <filename>");
	    System.exit(-1);
	}
	return res;
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
	Main mainProgram = new Main(args);
	mainProgram.run();
    }
}
