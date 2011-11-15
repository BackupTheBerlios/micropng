package micropng;

import micropng.userinterface.Runner;
import micropng.userinterface.invocationline.InvocationLineEvaluator;

public class Main {
    public static void main(String[] args) {
	InvocationLineEvaluator invocationLineEvaluator = new InvocationLineEvaluator();
	Runner runner = new Runner();
	invocationLineEvaluator.evaluate(args);
	runner.launch();
    }
}
