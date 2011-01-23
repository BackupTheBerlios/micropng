package micropng.micropng;

import java.io.IOException;

import micropng.userinterface.UserConfiguration;
import micropng.userinterface.cli.CommandLineParser;
import micropng.userinterface.cli.OutputChannel;
import micropng.userinterface.cli.WrongUsageException;

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
	    UserConfiguration u = parser.parse();
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
