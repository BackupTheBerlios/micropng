// deprecated, use micropng.userinterface.* for main

package micropng.micropng;

import java.io.IOException;

import micropng.userinterface.InternalConfiguration;
import micropng.userinterface.UserConfiguration;
import micropng.userinterface.cli.CommandLineParser;

public class Main {

    //OutputChannel out;
    String[] args;

    public Main(String[] args) {
	//out = new OutputChannel();
	this.args = args;
    }

    public void run() throws IOException {

    }

    private InternalConfiguration configure() {
	InternalConfiguration res = null;
	CommandLineParser parser = new CommandLineParser(args);
        UserConfiguration c = parser.parse();
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
