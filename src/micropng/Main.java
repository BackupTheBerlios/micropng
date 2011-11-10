package micropng;

import micropng.userinterface.InvocationLineEvaluator;
import micropng.userinterface.Runner;

public class Main {
    public static void main(String[] args) {
	InvocationLineEvaluator invocationLineEvaluator = new InvocationLineEvaluator();
	Runner runner = new Runner();
	invocationLineEvaluator.evaluate(args);
	runner.launch();
    }
}
