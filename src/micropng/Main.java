package micropng;

import micropng.userinterface.Runner;
import micropng.userinterface.UserConfiguration;
import micropng.userinterface.invocationline.InvocationLineEvaluator;

public class Main {
    public static void main(String[] args) {
	InvocationLineEvaluator invocationLineEvaluator = new InvocationLineEvaluator();
	UserConfiguration userConfiguration = invocationLineEvaluator.evaluate(args);
	Runner runner = new Runner();

	runner.launchInterface(userConfiguration);
    }
}
