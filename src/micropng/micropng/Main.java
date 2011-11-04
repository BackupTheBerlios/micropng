// deprecated, use micropng.userinterface.* for main

package micropng.micropng;

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

    public void run() {

    }

    private InternalConfiguration configure() {
	InternalConfiguration res = null;
	CommandLineParser parser = new CommandLineParser(args);
        UserConfiguration c = parser.parse();
	return res;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	Main mainProgram = new Main(args);
	mainProgram.run();
    }
}
