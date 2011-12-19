package micropng;

import micropng.userinterface.Runner;
import micropng.userinterface.UserConfiguration;
import micropng.userinterface.invocationline.InvocationLineEvaluator;

public class Main {
    public static void main(String[] args) {
	UserConfiguration userConfiguration;
	InvocationLineEvaluator invocationLineEvaluator = new InvocationLineEvaluator();
	Runner runner = new Runner();
	userConfiguration = invocationLineEvaluator.evaluate(args);
	runner.launch(userConfiguration);
    }
}
